package com.example.demowebclient.service;

import com.example.demowebclient.utils.json.JsonPathPropertiesHelper;
import com.jayway.jsonpath.*;
import com.example.demowebclient.utils.rest.WebClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class CVEDataService {

    public static final String VALUE_NOT_FOUND_FOR = "Value not found for : {}";
    public final ConfigurationURL configurationURL;
    private final JsonPathPropertiesHelper jsonPathPropertiesHelperValues;
    private final WebClientUtils webClientUtils;
    private static final Logger log = LoggerFactory.getLogger(CVEDataService.class);
    public static final String AN_ERROR_OCCURRED = "An error occurred: {}";

    public CVEDataService(JsonPathPropertiesHelper jsonPathPropertiesHelperValues, ConfigurationURL configurationURL, WebClientUtils webClientUtils) {
        this.jsonPathPropertiesHelperValues = jsonPathPropertiesHelperValues;
        this.webClientUtils = webClientUtils;
        this.configurationURL = configurationURL;
    }

    public void fetchCVEDataResults(String cvdId) {
        Map<String, String> param = Collections.singletonMap("cveId", cvdId);
        Mono<String> responseString = webClientUtils.makeWebClientCall(configurationURL.getCveIdHost(), configurationURL.getCveIdtPath(), param);
        responseString.subscribe(
                this::parseResponse,
                error -> log.error(AN_ERROR_OCCURRED, error.getMessage())
        );
    }

    private void parseResponse(String response) {
        if (response != null && !response.isEmpty()) {

            Configuration configuration = Configuration.builder()
                    .options(Option.DEFAULT_PATH_LEAF_TO_NULL, Option.SUPPRESS_EXCEPTIONS)
                    .build();

            DocumentContext documentContext = JsonPath.using(configuration).parse(response);
            Map<String, String> propertiesMap = jsonPathPropertiesHelperValues.getAllProperties();
            fetchValuesFromJSONResponse(documentContext, propertiesMap);

        } else {
            log.error("Empty response returned from Get API");
        }

    }

    private void fetchValuesFromJSONResponse(DocumentContext documentContext, Map<String, String> propertiesMap) {

        for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
            log.info(entry.getKey());
            Object queryResult;
            try {
                queryResult = documentContext.read(entry.getValue());
                if (queryResult instanceof List<?> queryResultList) {
                    List<Object> resultList = queryResultList.stream()
                            .map(Object.class::cast)
                            .toList();
                    fetchFromJSONArray(entry, resultList);
                } else {
                    String queryResultStr = String.valueOf(queryResult);
                    if (queryResultStr.isEmpty()) {
                        log.error(VALUE_NOT_FOUND_FOR, entry.getKey());
                    } else {
                        log.info(queryResultStr);
                    }
                }
            } catch (InvalidPathException e) {
                log.error("Invalid JSON path: {}", e.getMessage());
            }
        }

    }

    private void fetchFromJSONArray(Map.Entry<String, String> entry, List<Object> resultList) {

        if (!resultList.isEmpty()) {
            resultList.forEach(
                    element -> {
                        String stringValue = Optional.ofNullable(element).map(Object::toString).orElse("");
                        if (stringValue.isEmpty()) {
                            log.error(VALUE_NOT_FOUND_FOR, entry.getKey());
                        } else {
                            log.info(stringValue);
                        }
                    });
        } else {
            log.error(VALUE_NOT_FOUND_FOR, entry.getKey());
        }

    }
}

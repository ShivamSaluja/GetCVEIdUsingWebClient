package com.example.demowebclient.service;

import com.example.demowebclient.exception.ApiExceptionHandler;
import com.example.demowebclient.utils.json.JSONPath;
import com.example.demowebclient.utils.json.JSONUtils;
import com.example.demowebclient.utils.rest.WebClientUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@Service
public class CVEDataService {

    public final ConfigurationURL configurationURL;
    private final JSONPath jsonPathValues;
    private final WebClientUtils webClientUtils;
    private static final Logger log = LoggerFactory.getLogger(CVEDataService.class);
    public static final String AN_ERROR_OCCURRED= "An error occurred: {}";
    public static final String FAILED_TO_PARSE_RESPONSE_JSON = "Failed to Parse response JSON";

    public CVEDataService(JSONPath jsonPathValues, ConfigurationURL configurationURL, WebClientUtils webClientUtils) {
        this.jsonPathValues = jsonPathValues;
        this.webClientUtils = webClientUtils;
        this.configurationURL = configurationURL;
    }

    public void fetchCVEDataResults(String cvdId)  {
        Map<String, String> param = Collections.singletonMap("cveId", cvdId);
        Mono<String> responseString = webClientUtils.makeWebClientCall(configurationURL.getCveIdHost(),configurationURL.getCveIdtPath(), param);
        responseString.subscribe(
                this::accept,
                error -> log.error(AN_ERROR_OCCURRED,error.getMessage())
        );
    }

    private void accept(String response)  {
        if (response != null && !response.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = null;
            try {
                rootNode = objectMapper.readTree(response);
            } catch (JsonProcessingException e) {
                log.error(FAILED_TO_PARSE_RESPONSE_JSON);
                throw new ApiExceptionHandler(FAILED_TO_PARSE_RESPONSE_JSON);
            }

            JsonNode vulnerabilities = rootNode.at(jsonPathValues.getVulnerabilities());

            if (vulnerabilities.isMissingNode() || vulnerabilities.isNull())
                log.error(JSONUtils.MISSING_NODE_MESSAGE, jsonPathValues.getVulnerabilities());

            if (vulnerabilities.size() == 0)
                log.info("No vulnerabilities exist for this CVE Id");

            else {
                for (JsonNode val : vulnerabilities) {

                    log.info("CVE-ID : ");
                    JSONUtils.valueAtJSONNodePath(val, jsonPathValues.getCveId());

                    log.info("Descriptions : ");
                    JSONUtils.valueAtJSONArrayNodePath(val, jsonPathValues.getDescriptions(), jsonPathValues.getValues());

                    log.info("CVSS Vector String V31 : ");
                    JSONUtils.valueAtJSONArrayNodePath(val, jsonPathValues.getCvssMetricsV31(), jsonPathValues.getCvssVectorString());

                    log.info("CVSS Vector String V2 : ");
                    JSONUtils.valueAtJSONArrayNodePath(val, jsonPathValues.getCvssMetricsV2(), jsonPathValues.getCvssVectorString());

                }
            }

            log.info("Timestamp : ");
            JSONUtils.valueAtJSONNodePath(rootNode, jsonPathValues.getTimestamp());
        } else {
            log.error("Empty response returned from Get API ");
        }

    }
}

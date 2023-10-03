package com.example.demowebclient.utils.rest;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;


@Component
public class WebClientUtils {

    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(WebClientUtils.class);
    public static final String AN_ERROR_OCCURRED_HTTP_STATUS = "An error occurred: {}";

    public static final String HTTPS = "https";

    public WebClientUtils(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @CircuitBreaker(name = "makeWebClientCall", fallbackMethod = "fallbackMakeWebClientCall")
    public String makeWebClientCall(String host, String path, Map<String, String> requestParams) {

        return webClient
                .get()
                .uri(uriBuilder -> {
                            uriBuilder
                                    .scheme(HTTPS)
                                    .host(host)
                                    .path(path);

                            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                                uriBuilder.queryParam(entry.getKey(), entry.getValue());
                            }

                            log.info("Get request API URL : {}", uriBuilder.build());

                            return uriBuilder.build();
                        }
                )
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new HttpClientErrorException(HttpStatus.NOT_FOUND,  "Entity not found."));
                    } else {
                        return Mono.error(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
                    }
                })
                .bodyToMono(String.class)
                .block();
    }


    public String fallbackMakeWebClientCall(Throwable throwable) {
        log.error(AN_ERROR_OCCURRED_HTTP_STATUS, throwable.getMessage());
        return  throwable.getMessage();
    }
}


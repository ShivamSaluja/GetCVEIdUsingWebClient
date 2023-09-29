package com.example.demowebclient.utils.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
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

    public Mono<String> makeWebClientCall(String host, String path, Map<String, String> requestParams) {

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
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(IOException.class, this::handleIOException)
                .onErrorResume(ResourceAccessException.class, this::handleResourceAccessException)
                .onErrorResume(HttpClientErrorException.class, this::handleHttpClientErrorException)
                .onErrorResume(Exception.class, this::handleGenericException);

    }

    private Mono<String> handleIOException(IOException ioException) {

        log.error(AN_ERROR_OCCURRED_HTTP_STATUS, "IOException  occurred : " + ioException.getMessage());

        return Mono.just("Error: An IOException occurred");
    }

    private Mono<String> handleResourceAccessException(ResourceAccessException resourceAccessException) {

        log.error(AN_ERROR_OCCURRED_HTTP_STATUS, "ResourceAccessException  occurred : " + resourceAccessException.getMessage());

        return Mono.just("Error: An ResourceAccessException occurred");
    }

    private Mono<String> handleHttpClientErrorException(HttpClientErrorException handleHttpClientErrorException) {

        log.error(AN_ERROR_OCCURRED_HTTP_STATUS, "HttpClientErrorException  occurred : " + handleHttpClientErrorException.getMessage());

        return Mono.just("Error: An HttpClientErrorException occurred");
    }

    private Mono<String> handleGenericException(Exception exception) {

        log.error(AN_ERROR_OCCURRED_HTTP_STATUS, exception.getMessage());

        return Mono.just("Error: Something went wrong");
    }
}

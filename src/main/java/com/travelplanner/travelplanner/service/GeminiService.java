package com.travelplanner.travelplanner.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class GeminiService {

    @Autowired
    private WebClient geminiWebClient;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final String modelName = "gemini-2.0-flash";

    public Mono<String> generateContent(String prompt) {

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))));

        // Adjust the URI to match the curl command
        return geminiWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1beta/models/{model}:generateContent") // Keep path
                        .queryParam("key", geminiApiKey)// Pass key as query parameter
                        .build(modelName))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> {
                    return Mono.error(new RuntimeException("Failed to call Gemini API"));
                });
    }
}

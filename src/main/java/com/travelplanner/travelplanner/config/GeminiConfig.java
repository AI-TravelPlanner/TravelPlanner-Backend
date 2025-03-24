package com.travelplanner.travelplanner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GeminiConfig {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Bean
    public WebClient geminiWebClient(WebClient.Builder webClientBuilder) {

        if (geminiApiKey == null || geminiApiKey.isEmpty()) {
            throw new IllegalArgumentException("Gemini API key is not set");
        }

        System.out.println("Gemini API key is set and valid.");

        return WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("X-GEMINI-APIKEY", geminiApiKey)
                .build();
    }
}
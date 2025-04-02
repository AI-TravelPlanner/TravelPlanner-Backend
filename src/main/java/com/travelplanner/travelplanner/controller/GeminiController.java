package com.travelplanner.travelplanner.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelplanner.travelplanner.service.GeminiService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    /**
     * Endpoint to generate content based on a given prompt.
     * 
     * @param request A map containing the "prompt" key with the input string.
     * @return A Mono<JsonNode> containing the formatted JSON response.
     */
    @PostMapping("/generate")
    public Mono<JsonNode> generate(@RequestBody Map<String, Object> request) {
        // Generate content, parse the response, and format it

        return geminiService.planTrip(request);
    }

}

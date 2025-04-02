package com.travelplanner.travelplanner.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;

@Service
public class TripPlanService {

    @Autowired
    private GeminiService geminiService;

    public Mono<JsonNode> planTrip(Map<String, Object> request) {

        // Call the Gemini service to get the trip plan
        return geminiService.planTrip(request);
    }
}

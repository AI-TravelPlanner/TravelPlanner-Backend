package com.travelplanner.travelplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelplanner.travelplanner.service.GeminiService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/gemini")
public class PlanTrip {

    @Autowired
    private GeminiService geminiService;

    //this one is to plan and show the trip to the user
    @PostMapping("/plan-trip")
    public ResponseEntity<String> planTrip() {
        String prompt = "Generate a summary of the latest trends in AI.";
        Mono<String> content = geminiService.generateContent(prompt);
        return ResponseEntity.ok(content.block());
    }

}

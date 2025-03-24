package com.travelplanner.travelplanner.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelplanner.travelplanner.service.GeminiService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/generate")
    public Mono<String> generate(@RequestBody Map<String, Object> request) {
        String prompt = (String) request.get("prompt");
        return geminiService.generateContent(prompt);
    }
}

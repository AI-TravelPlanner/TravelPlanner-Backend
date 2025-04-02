package com.travelplanner.travelplanner.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelplanner.travelplanner.model.response.PlanTripResponse;
import com.travelplanner.travelplanner.service.TripPlanService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/gemini")
public class PlanTripController {

    @Autowired
    private TripPlanService tripPlanService;

    // this one is to plan and show the trip to the user
    @PostMapping("/plan-trip")
    public ResponseEntity<PlanTripResponse> planTripController(@RequestBody Map<String, Object> request) {

        Mono<JsonNode> plannedtrip = tripPlanService.planTrip(request);

        PlanTripResponse response = new PlanTripResponse();

        plannedtrip.subscribe(json -> {
            // Handle the response here if needed
            response.setMessage("Trip planned successfully");
            response.setJsonNode(json);
        }, error -> {
            // Handle any errors here
            response.setMessage("Error planning trip: " + error.getMessage());
            response.setJsonNode(null);
        });

        return ResponseEntity.ok(response);
    }

}

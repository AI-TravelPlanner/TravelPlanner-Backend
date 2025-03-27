package com.travelplanner.travelplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelplanner.travelplanner.service.TripPlanService;

@RestController
@RequestMapping("/api/v1/gemini")
public class PlanTrip {

    @Autowired
    private TripPlanService tripPlanService;

    // this one is to plan and show the trip to the user
    @PostMapping("/plan-trip")
    public ResponseEntity<String> planTrip(@RequestBody PlanTrip planTrip) {
        return ResponseEntity.ok("Trip planned successfully");
    }

}

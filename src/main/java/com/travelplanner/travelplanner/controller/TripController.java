package com.travelplanner.travelplanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.travelplanner.travelplanner.model.Trip;
import com.travelplanner.travelplanner.service.TripService;

@RestController
@RequestMapping("/api/v1")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping("/create-trip")
    public ResponseEntity<String> addNewTrip(@RequestBody Trip trip) {
        if (tripService.createTrip(trip)) {
            return ResponseEntity.ok("Trip created successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to create trip");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable("id") String id) {
        Trip trip = tripService.getTripById(id);
        return ResponseEntity.ok(trip);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrip(@PathVariable("id") String id) {
        if (tripService.deleteTrip(id)) {
            return ResponseEntity.ok("Trip deleted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete trip");
        }
    }
}

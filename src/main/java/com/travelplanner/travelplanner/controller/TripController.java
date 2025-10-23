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

import com.travelplanner.travelplanner.model.trip.Trip;
import com.travelplanner.travelplanner.service.TripService;

@RestController
@RequestMapping("/api/v1")
public class TripController {

    /**
     * Service layer that contains business logic for Trip operations.
     * Injected by Spring.
     */
    @Autowired
    private TripService tripService;


    /**
     * Create a new Trip.
     *
     * POST /api/v1/create-trip
     *
     * @param trip Trip object deserialized from the request body
     * @return 200 OK with success message on success, 400 Bad Request on failure
     */
    @PostMapping("/create-trip")
    public ResponseEntity<String> addNewTrip(@RequestBody Trip trip) {
        if (tripService.createTrip(trip)) {
            return ResponseEntity.ok("Trip created successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to create trip");
        }
    }

    /**
     * Retrieve all trips.
     *
     * GET /api/v1/all
     *
     * @return 200 OK with list of trips
     */

    @GetMapping("/all")
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return ResponseEntity.ok(trips);
    }


    /**
     * Retrieve a trip by its ID.
     *
     * GET /api/v1/{id}
     *
     * @param id Trip ID from the URL path
     * @return 200 OK with the Trip object, or 404 Not Found if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable("id") String id) {
        Trip trip = tripService.getTripById(id);
        return ResponseEntity.ok(trip);
    }


    /**
     * Delete a trip by its ID.
     *
     * DELETE /api/v1/{id}
     *
     * @param id Trip ID from the URL path
     * @return 200 OK with success message on success, 400 Bad Request on failure
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrip(@PathVariable("id") String id) {
        if (tripService.deleteTrip(id)) {
            return ResponseEntity.ok("Trip deleted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete trip");
        }
    }
}

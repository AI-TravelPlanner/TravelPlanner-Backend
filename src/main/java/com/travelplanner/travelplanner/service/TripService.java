package com.travelplanner.travelplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelplanner.travelplanner.model.trip.Trip;
import com.travelplanner.travelplanner.repository.TripRepo;

@Service
public class TripService {

    @Autowired
    private TripRepo tripRepo;

    //only to save the trip to the database
    public boolean createTrip(Trip trip) {
        Trip savedTrip = tripRepo.save(trip);
        return savedTrip != null && savedTrip.getId() != null;
    }

    public List<Trip> getAllTrips() {
        return tripRepo.findAll();
    }

    public Trip getTripById(String id) {
        return tripRepo.findById(id).orElse(null);
    }

    public Trip getTripByName(String tripName) {
        return tripRepo.findByTripName(tripName).orElse(null);
    }

    public boolean deleteTrip(String id) {
        tripRepo.deleteById(id);
        return tripRepo.findById(id).isEmpty();
    }

}

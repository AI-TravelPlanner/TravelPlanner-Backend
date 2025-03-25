package com.travelplanner.travelplanner.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.travelplanner.travelplanner.model.trip.Trip;

import java.util.Optional;

@Repository
public interface TripRepo extends MongoRepository<Trip, String> {

    Optional<Trip> findByTripName(String tripName);

}

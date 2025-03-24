package com.travelplanner.travelplanner.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "hotelDetails")
@NoArgsConstructor
@AllArgsConstructor
public class HotelDetails {

    private String hotelName;
    private String location;
    private double pricePerNight;
    // private String amenities;
    // private String contactNumber;
}

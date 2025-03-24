package com.travelplanner.travelplanner.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

    @Id
    private String id;

    private String tripName;
    private String location;
    private Date startDate;
    private Integer numberOfPeople;
    private Integer duration;
    private double budget;
    private String tripType;


    @Field("dailyItinery")  // This matches with your database field name
    private List<DailyItinery> dailyItinery;

    @Field("weather")
    private Weather weather;

    @Field("hotelDetails")
    private HotelDetails hotelDetails;



}

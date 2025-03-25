package com.travelplanner.travelplanner.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    private String tripName;
    private String location;
    private Date startDate;
    private Integer numberOfPeople;
    private Integer duration;
    private double budget;
    private String tripType;
}

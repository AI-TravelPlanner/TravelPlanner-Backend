package com.travelplanner.travelplanner.model.trip;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "weather")
public class Weather {

    private double temperature;
    private String condition;
    // private double humidity;
    // private String windSpeed;

}

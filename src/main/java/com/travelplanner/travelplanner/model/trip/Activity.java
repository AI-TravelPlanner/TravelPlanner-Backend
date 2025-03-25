package com.travelplanner.travelplanner.model.trip;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "activities")
public class Activity {

    private String name;
    private String time;
    private String description;
}

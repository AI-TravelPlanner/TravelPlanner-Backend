package com.travelplanner.travelplanner.model.trip;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "dailyItinery")
public class DailyItinery {

    private Integer day;
    private List<Activity> activities;
}

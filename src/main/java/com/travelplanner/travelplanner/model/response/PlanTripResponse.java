package com.travelplanner.travelplanner.model.response;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanTripResponse {

    private String message;
    private JsonNode jsonNode;

}

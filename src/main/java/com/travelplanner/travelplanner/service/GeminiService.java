package com.travelplanner.travelplanner.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import reactor.core.publisher.Mono;

@Service
public class GeminiService {

  @Autowired
  private WebClient geminiWebClient; // WebClient instance for making HTTP requests to the Gemini API.

  // ObjectMapper instance for JSON parsing and manipulation
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Value("${gemini.api.key}")
  private String geminiApiKey; // API key for authenticating requests to the Gemini API.

  private final String modelName = "gemini-2.0-flash"; // The name of the Gemini model being used.

  public Mono<JsonNode> planTrip(Map<String, Object> request) {
    // Extract the "prompt" value from the request body
    String prompt = (String) request.get("prompt");

    // Generate content, parse the response, and format it
    return formatTrip(extractAndParseJson(Mono.just(generateContent(prompt).block())));
  }

  // all Helper method are below

  /**
   * Helper method to load the JSON schema from a file in the classpath.
   * This schema is used to validate the response from the Gemini API.
   *
   * @return The JSON schema as a String.
   * @throws RuntimeException if the schema file cannot be loaded.
   */
  private String loadSchema() {
    try {
      // Load the schema file from the classpath.
      ClassPathResource resource = new ClassPathResource("GeminiSchema.json");
      InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
      System.out.println("Schema loaded successfully");
      return FileCopyUtils.copyToString(reader); // Convert the file content to a String.
    } catch (IOException e) {
      // Throw a runtime exception if the schema cannot be loaded.
      throw new RuntimeException("Failed to load schema", e);
    }
  }

  /**
   * Method to interact with the Gemini API and generate content based on the
   * provided prompt.
   *
   * @param prompt The input text prompt for content generation.
   * @return A Mono containing the API response as a String.
   */
  public Mono<String> generateContent(String prompt) {

    // Load the JSON schema for response validation.
    String schema = loadSchema();

    // Construct the request body for the Gemini API.
    String requestBody = String.format("""
        {
          "contents": [
            {
              "parts": [
                {
                  "text": "%s"
                }
              ]
            }
          ],
          "generationConfig": {
            "response_mime_type": "application/json",
            "response_schema": %s
          }
        }
        """, prompt, schema);

    System.out.println(prompt + "============================================================");

    // Make a POST request to the Gemini API and handle the response.
    return geminiWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path("/v1beta/models/gemini-2.0-flash:generateContent") // API endpoint for content generation.
            .queryParam("key", geminiApiKey) // Add the API key as a query parameter.
            .build(modelName))
        .bodyValue(requestBody) // Set the request body.
        .retrieve() // Retrieve the response.
        .bodyToMono(String.class) // Convert the response body to a Mono<String>.
        .onErrorResume(e -> Mono.error(new RuntimeException("Failed to call Gemini API", e))); // Handle errors.
  }

  /**
   * Utility method to extract and parse a JSON response.
   * 
   * @param responseMono A Mono containing the raw JSON string response.
   * @return A Mono<JsonNode> containing the parsed JSON object.
   */
  public static Mono<JsonNode> extractAndParseJson(Mono<String> responseMono) {
    return responseMono.map(response -> {
      try {
        // Parse the root JSON object from the response string
        JsonNode rootNode = objectMapper.readTree(response);

        // Navigate to the specific "text" field within the JSON structure
        JsonNode textNode = rootNode
            .path("candidates") // Access the "candidates" array
            .path(0) // Get the first candidate
            .path("content") // Access the "content" field
            .path("parts") // Access the "parts" array
            .path(0) // Get the first part
            .path("text"); // Access the "text" field

        // Extract the text field's value, which is a JSON string, and parse it into a
        // JSON object
        return objectMapper.readTree(textNode.asText());
      } catch (Exception e) {
        // Throw a runtime exception if parsing fails
        throw new RuntimeException("Failed to parse JSON", e);
      }
    });
  }

  /**
   * Formats the parsed JSON response into a structured format.
   * 
   * @param inputJson A Mono<JsonNode> containing the parsed JSON response.
   * @return A Mono<JsonNode> containing the formatted JSON object.
   */
  public Mono<JsonNode> formatTrip(Mono<JsonNode> inputJson) {
    return inputJson.map(this::restructureJson);
  }

  /**
   * Restructures the input JSON into a more organized format.
   * 
   * @param input The input JSON object containing trip details.
   * @return A formatted JSON object with organized trip details.
   */
  private JsonNode restructureJson(JsonNode input) {
    ObjectNode output = objectMapper.createObjectNode();

    // Map basic trip details with default values for missing fields
    output.put("tripName", input.path("tripName").asText("Unknown Trip"));
    output.put("location", input.path("location").asText("Unknown Location"));
    output.put("startDate", input.path("startDate").asText("2025-06-15"));
    output.put("numberOfPeople", input.path("numberOfPeople").asInt(1));
    output.put("duration", input.path("duration").asInt(1));
    output.put("budget", input.path("budget").asDouble(0));
    output.put("tripType", input.path("tripType").asText("vacation"));

    // Include daily itinerary as-is
    output.set("dailyItinery", input.path("dailyItinery"));

    // Format weather details with default values for missing fields
    ObjectNode weatherNode = objectMapper.createObjectNode();
    JsonNode weather = input.path("weather");
    weatherNode.put("temperature", weather.path("temperature").asInt(0));
    weatherNode.put("condition", weather.path("condition").asText("Unknown"));
    output.set("weather", weatherNode);

    // Format hotel details with default values for missing fields
    ObjectNode hotelNode = objectMapper.createObjectNode();
    JsonNode hotel = input.path("hotelDetails");
    hotelNode.put("hotelName", hotel.path("hotelName").asText("Unknown Hotel"));
    hotelNode.put("location", hotel.path("location").asText("Unknown Location"));
    hotelNode.put("pricePerNight", hotel.path("pricePerNight").asDouble(0));
    output.set("hotelDetails", hotelNode);

    return output;
  }
}

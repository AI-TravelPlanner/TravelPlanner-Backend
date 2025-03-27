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
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;

import reactor.core.publisher.Mono;

@Service
public class GeminiService {

    @Autowired
    private WebClient geminiWebClient;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final String modelName = "gemini-2.0-flash";

    // helper to load schema from resouser
    private String loadSchema() {
        try {
            ClassPathResource resource = new ClassPathResource("GeminiSchema.json");
            InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            System.out.println("schema loaded succesfullfy");
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load schema", e);
        }
    }

    // private void validateResponse(String response, String schema) throws IOException {
    //     JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
    //     JsonSchema jsonSchema = factory.getSchema(schema);
    //     JsonNode jsonNode = objectMapper.readTree(response);

    //     Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);

    //     if (!errors.isEmpty()) {
    //         System.err.println("Validation errors:");
    //         errors.forEach(System.err::println);
    //         throw new RuntimeException("Response does not match schema.");
    //     } else {
    //         System.out.println("Response matches schema.");
    //     }
    // }


    // the important method for interacting with Gemini API
    public Mono<String> generateContent(String prompt) {

        String schema = loadSchema();


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

        return geminiWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        // .path("/v1beta/models/gemini-2.0-flash:generateContent")
                        .path("/v1beta/models/gemini-2.0-flash:generateContent")
                        .queryParam("key", geminiApiKey)
                        .build(modelName))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to call Gemini API", e)));
    }

}

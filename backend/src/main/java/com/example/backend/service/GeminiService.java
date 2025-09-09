package com.example.backend.service;

import com.example.backend.model.gemini.GeminiRequest;
import com.example.backend.model.gemini.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GeminiService implements AIService {

    @Value("${ai.gemini.base-url}")
    private String baseUrl;   // e.g. https://generativelanguage.googleapis.com/v1beta

    @Value("${ai.gemini.api-key}")
    private String apiKey;

    @Value("${ai.gemini.model}")
    private String model;     // e.g. gemini-1.5-flash

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String generateResponse(String prompt) {
        // Build the request URL by combining base + /models/{model}:generateContent
        String url = baseUrl + "/models/" + model + ":generateContent?key=" + apiKey;

        GeminiRequest request = new GeminiRequest(
                List.of(new GeminiRequest.Content(
                        List.of(new GeminiRequest.Part(prompt))
                ))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<GeminiResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, new HttpEntity<>(request, headers), GeminiResponse.class
        );

        return response.getBody().candidates().get(0).content().parts().get(0).text();
    }
}
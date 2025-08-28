package com.example.generator.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class HuggingFaceRestClient {

    @Autowired
    private WebClient webClient;

    public byte[] generate(String prompt) {
        String requestBody = "{ \"inputs\": \"" + prompt + "\" }";

        byte[] imageBytes = webClient.post()
                .uri("/black-forest-labs/FLUX.1-dev") // make sure model is included
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();

        // Assuming the API returns {"generated_image": "<BASE64>"}
        System.out.println("The api response is " + imageBytes);
        try {
            Files.write(Paths.get("output.jpg"), imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBytes;
    }
}

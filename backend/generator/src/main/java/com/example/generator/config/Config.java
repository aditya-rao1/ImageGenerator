package com.example.generator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Value("${hugging.api.key}")
    private String huggingFaceApiKey;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                        .baseUrl("https://api-inference.huggingface.co/models")
                        .defaultHeader("Authorization", "Bearer " + huggingFaceApiKey)
                        .build(); 
    }
}
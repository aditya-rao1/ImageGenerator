package com.example.generator.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.generator.model.RequestDTO;

@Component
public class FastRestClient {

    private WebClient fasClient;

    public FastRestClient(@Qualifier("fastClient") WebClient fasClient) {
        this.fasClient = fasClient;    
    }

    public byte[] retrieveImage(@RequestBody RequestDTO request) {
        byte[] imageBytes = fasClient.post().uri("/generate").bodyValue(request).retrieve().bodyToMono(byte[].class).block();
        return imageBytes;
    }
}

package com.example.generator.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.UUID;


//TODO: Figure out configuration issues next time and then test out the actual posting to the bucket
@Component
public class SupaBaseRestClient {
    
    private final WebClient supaClient;

    @Value("${bucket.name}")
    private String supaBucketName;

    public SupaBaseRestClient(@Qualifier("supaClient") WebClient supaClient) {
        this.supaClient = supaClient;
    }

    public String saveImage(byte[] imageBytes) {
        //Generate unique fileName 
        String fileName = UUID.randomUUID().toString() + ".png";

        //Just going to be where the image is stored in the bucket
        String objectPath = supaBucketName + "/" + fileName;
    
        // Perform upload request
        String response = supaClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/storage/v1/object/" + objectPath)
                        .build())
                .contentType(MediaType.IMAGE_PNG)
                .header("x-upsert", "false") // change to true if you want to overwrite files
                .bodyValue(imageBytes)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("Supabase response: " + response);
        return response;
    }
}

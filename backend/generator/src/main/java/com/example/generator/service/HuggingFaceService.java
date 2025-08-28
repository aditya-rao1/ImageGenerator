package com.example.generator.service;

import com.example.generator.client.HuggingFaceRestClient;
import org.springframework.stereotype.Service;

@Service
public class HuggingFaceService {
    
    private final HuggingFaceRestClient client;

    public HuggingFaceService(HuggingFaceRestClient client) {
        this.client = client;
    }
    

    public byte[] generate(String prompt) {
        return client.generate(prompt);
    }


    public byte[] saveToFile(String prompt) {
        byte[] imageBytes = client.generate(prompt);
        
        SupabaseService supabaseService = new SupabaseService(imageBytes); 

        //TODO: The workflow is 
        return null;
    }
    
}

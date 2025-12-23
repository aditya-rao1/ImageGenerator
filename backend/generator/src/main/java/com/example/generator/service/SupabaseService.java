package com.example.generator.service;

import org.springframework.stereotype.Service;

import com.example.generator.client.FastRestClient;
import com.example.generator.client.SupaBaseRestClient;
import com.example.generator.model.RequestDTO;

import lombok.Value;


@Service
public class SupabaseService {

    private final SupaBaseRestClient supaClient;

    private final FastRestClient fastRestClient;


    public SupabaseService(SupaBaseRestClient supaClient, FastRestClient fastRestClient) {
        this.supaClient = supaClient;
        this.fastRestClient = fastRestClient;
    }

    public byte[] getImage(String prompt) {
        RequestDTO requestDTO = new RequestDTO(prompt);
        return fastRestClient.retrieveImage(requestDTO);
    }

    public String saveImage(byte[] imageBytes) {
        return supaClient.saveImage(imageBytes);
    }
}

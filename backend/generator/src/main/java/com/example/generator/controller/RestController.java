package com.example.generator.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.generator.service.SupabaseService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@Controller
@RequestMapping("/api")
public class RestController {

    private SupabaseService supabaseService;
    private boolean useActualModel;

    public RestController(SupabaseService supabaseService) {
        this.supabaseService = supabaseService; 
        this.useActualModel = true;
    }

    @GetMapping("/generate-image")
    public ResponseEntity<byte[]> getImageBytes(@RequestParam String prompt) { // As a note this is just a test run of the getting the imageByes to make sure the generator, everything else works
        byte[] imageBytes = supabaseService.getImage(prompt);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG); // or JPEG depending on what you expect
        headers.setContentLength(imageBytes.length);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/post-image")
    public String saveToSupa(@RequestParam(required = false) String prompt) throws IOException { //TODO: Implement the full flow of the application 
        byte[] imageBytes;
        String sampleImagePath = "C:/Users/raoad/OneDrive/Documents/PersonalProjects/ImageGenerator/lion.png";

        if(useActualModel) imageBytes = supabaseService.getImage(prompt);
        else imageBytes = convertImagePathToBytes(sampleImagePath); // when not testing the ai model go ahead and just flip the boolean and use the other image as a placeholder
        return supabaseService.saveImage(imageBytes);
    }
    

    private byte[] convertImagePathToBytes(String path) throws IOException {
        File file = new File(path);
        BufferedImage bufferedImage = ImageIO.read(file);

        WritableRaster raster = bufferedImage .getRaster();
        DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

        return data.getData();
    }

    
    
}
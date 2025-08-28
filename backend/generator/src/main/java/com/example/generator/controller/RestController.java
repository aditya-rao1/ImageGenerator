package com.example.generator.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import com.example.generator.service.HuggingFaceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/api")
public class RestController {
    private HuggingFaceService huggingFaceService;

    public RestController(HuggingFaceService huggingFaceService) { 
        this.huggingFaceService = huggingFaceService;
    }

    @PostMapping("/save/{prompt}")
    public byte[] saveToFile(@PathVariable String prompt) {
        return huggingFaceService.saveToFile(prompt);
    }

    @GetMapping(value = "/generate/{prompt}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] generate(@PathVariable String prompt) {
        return huggingFaceService.generate(prompt);
    }
}
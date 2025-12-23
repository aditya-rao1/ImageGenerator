package com.example.generator.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

@Configuration
public class Config {

    @Value("${hugging.api.key}")
    private String huggingFaceApiKey;

    @Value("${supabase.url}")
    private String supaBaseUrl;

    @Value("${supabase.api.key}")
    private String supaBaseKey;

    @Value("${bucket.name}")
    private String bucketName;

    @Value("${deepai.api.key}")
    private String deepKey;

    @Value("${fast.api.url}")
    private String fastUrl;

    @Bean("supaClient")
    public WebClient supaClient() {
        return WebClient.builder().baseUrl(supaBaseUrl).defaultHeader("Authorization", "Bearer " + supaBaseKey)
                        .defaultHeader("apikey", supaBaseKey).build();
    }

    @Bean("fastClient")
    public WebClient fastClient() {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(50 * 1024 * 1024)) // 50 MB
            .build();
        return WebClient.builder().baseUrl(fastUrl).exchangeStrategies(strategies).build();
    }


}
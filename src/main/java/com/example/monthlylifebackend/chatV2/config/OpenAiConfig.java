package com.example.monthlylifebackend.chatV2.config;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {

    @Value("${openai.api-key}")
    private String apiKey;
    @Bean
    public String openAiApiKey() {
        return apiKey;
    }
    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(apiKey);
    }
}
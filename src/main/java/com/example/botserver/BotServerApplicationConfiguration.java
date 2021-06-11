package com.example.botserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ultimateai.chatclient.api.IntentsApi;
import ultimateai.chatclient.invoker.ApiClient;

@Configuration
public class BotServerApplicationConfiguration {
    @Value("${ai.ultimate.chatclient.apiKey}")
    private String apiKey;

    @Bean
    public RestTemplate restTemplate() {
         return new RestTemplate();
    }

    @Bean
    public ApiClient apiClient(RestTemplate restTemplate) {
        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setApiKey(apiKey);
        return apiClient;
    }

    @Bean
    public IntentsApi intentsApi(ApiClient apiClient) {
         return new IntentsApi(apiClient);
    }
}

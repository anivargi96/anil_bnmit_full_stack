package com.todo.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SlackService {

    private final String slackWebhookUrl;
    private final WebClient webClient = WebClient.create();

    public SlackService(@Value("${slack.webhook.url}") String slackWebhookUrl) {
        this.slackWebhookUrl = slackWebhookUrl;
    }

    public boolean postMessage(String message) {
        String payload = "{\"text\": \"" + message.replace("\"", "\\\"") + "\"}";

        try {
            webClient.post() .uri(slackWebhookUrl)
                     .header("Content-Type", "application/json")
                     .bodyValue(payload)
                     .retrieve()
                     .bodyToMono(String.class)
                     .block();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

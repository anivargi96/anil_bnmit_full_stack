package com.todo.api.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SummaryService {

    private final String openaiApiKey;
    private final WebClient webClient;

    public SummaryService(@Value("${openai.api.key}") String openaiApiKey) {
        this.openaiApiKey = openaiApiKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .build();
    }

    public String summarize(List<com.todo.api.model.Todo> todos) {
        String prompt = "Summarize these pending todos:\n" +
                todos.stream().map(com.todo.api.model.Todo::getTask).collect(Collectors.joining("\n"));

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        try {
            Map<?, ?> response = webClient.post()
                    .header("Authorization", "Bearer " + openaiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return ((Map<?, ?>) ((List<?>) response.get("choices")).get(0))
                    .get("message")
                    .toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error summarizing todos.";
        }
    }
}

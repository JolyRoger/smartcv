package org.torquemada.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Objects;

@Slf4j
@Component
public class OpenAiClient {

    @Value("${openai.api.key}")
    private String apiKey;

    public String callOpenAi(String prompt) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.openai.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        OpenAiRequest request = new OpenAiRequest(prompt);

        OpenAiResponse response = webClient.post()
                .uri("/v1/chat/completions")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("OpenAI error: " + errorBody))))
                .bodyToMono(OpenAiResponse.class)
                .block();

        return Objects.requireNonNull(response).getFirstResponse();
    }
}
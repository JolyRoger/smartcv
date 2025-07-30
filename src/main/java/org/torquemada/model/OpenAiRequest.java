package org.torquemada.model;

import lombok.Data;
import java.util.List;

@Data
public class OpenAiRequest {
    private final String model = "gpt-4o";
    private final List<Message> messages;

    public OpenAiRequest(String prompt) {
        messages = List.of(new Message("user", prompt));
    }

    public record Message(String role, String content) { }
}
package org.torquemada.model;

import lombok.Data;
import java.util.List;

public record OpenAiResponse(List<Choice> choices) {

    public String getFirstResponse() {
        return choices.getFirst().getMessage().getContent();
    }

    @Data
    public static class Choice {
        private Message message;
    }

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}
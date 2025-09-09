package com.example.backend.model.deepseek;

import java.util.List;

public record DeepSeekResponse(List<Choice> choices) {
    public record Choice(Message message) {}
    public record Message(String role, String content) {}
}

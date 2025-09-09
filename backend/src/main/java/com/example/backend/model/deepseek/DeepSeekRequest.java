package com.example.backend.model.deepseek;

import java.util.List;

public record DeepSeekRequest(String model, List<Message> messages) {
    public record Message(String role, String content) {}
}
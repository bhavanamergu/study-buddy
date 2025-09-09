package com.example.backend.service;

import com.example.backend.dto.HintRequest;
import org.springframework.stereotype.Service;

@Service
public class HintService {

    private final DeepSeekService deepSeekService;
    private final GeminiService geminiService;
    private final LlamaService llamaService;
    //private final OpenAiService openAiService;

    public HintService(DeepSeekService deepSeekService,
                       GeminiService geminiService,
                       LlamaService llamaService) {
        this.deepSeekService = deepSeekService;
        this.geminiService = geminiService;
        this.llamaService = llamaService;
        //this.openAiService = openAiService;
    }

    public String generateHint(HintRequest req) {
        String systemPrompt = """
            You are StudyBuddy — an educational DSA tutor.
            RULES:
            - Never give a full working solution unless the user explicitly asks for it.
            - Use progressive hinting:
              Level 1: short nudge (1–2 sentences) toward the idea.
              Level 2: name the algorithm/data structure + give high-level pseudocode outline.
              Level 3: implementation hints, key edge cases, and a code skeleton (no full, ready-to-run code).
            - Be concise and encourage the student to try.
            """;

        String userPrompt = """
            Problem:
            %s

            StudentAttempt:
            %s

            RequestedHintLevel: %d
            """.formatted(
                nullSafe(req.getProblemText()),
                nullSafe(req.getUserAttempt()),
                req.getHintLevel()
        );

        // ✅ Choose provider dynamically
        return switch (req.getProvider().toString().toLowerCase()) {
            case "deepseek" -> deepSeekService.generateResponse(systemPrompt + "\n" + userPrompt);
            case "gemini"   -> geminiService.generateResponse(systemPrompt + "\n" + userPrompt);
            case "llama"    -> llamaService.generateResponse(systemPrompt , userPrompt);
            //case "openai"   -> openAiService.generateResponse(systemPrompt + "\n" + userPrompt);
            default -> "Unsupported provider: " + req.getProvider();
        };
    }

    private String nullSafe(String s) {
        return (s == null || s.isBlank()) ? "No attempt provided" : s;
    }
}

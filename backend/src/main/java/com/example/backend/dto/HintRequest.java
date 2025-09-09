package com.example.backend.dto;

import com.example.backend.model.AIModelProvider;
import lombok.Data;

@Data
public class HintRequest {
    private String problemText;
    private String userAttempt;
    private int hintLevel; // 1..3
    private AIModelProvider provider;

    // Getter and Setter for problemText
    public String getProblemText() {
        return problemText;
    }

    public void setProblemText(String problemText) {
        this.problemText = problemText;
    }

    // Getter and Setter for userAttempt
    public String getUserAttempt() {
        return userAttempt;
    }

    public void setUserAttempt(String userAttempt) {
        this.userAttempt = userAttempt;
    }

    // Getter and Setter for hintLevel
    public int getHintLevel() {
        return hintLevel;
    }

    public void setHintLevel(int hintLevel) {
        this.hintLevel = hintLevel;
    }

    public AIModelProvider getProvider() { return provider; }
    public void setProvider(AIModelProvider provider) { this.provider = provider; }
}

package com.example.quiz.dto;

import com.example.quiz.model.QuestionType;
  // DTO to Add a question to a quiz
public class AddQuestionRequest {

    private QuestionType type;
    private String text;
    private Boolean correctTrueFalse;
    private String correctShortText;

    public QuestionType getType() { return type; }
    public void setType(QuestionType type) { this.type = type; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Boolean getCorrectTrueFalse() { return correctTrueFalse; }
    public void setCorrectTrueFalse(Boolean tf) { this.correctTrueFalse = tf; }

    public String getCorrectShortText() { return correctShortText; }
    public void setCorrectShortText(String st) { this.correctShortText = st; }
}

package com.example.quiz.dto;

import com.example.quiz.model.QuestionType;


// DTO to update question text/type/correct answers
public class UpdateQuestionRequest {

    private String text;
    private QuestionType type;
    private Boolean correctTrueFalse;
    private String correctShortText;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public QuestionType getType() { return type; }
    public void setType(QuestionType type) { this.type = type; }

    public Boolean getCorrectTrueFalse() { return correctTrueFalse; }
    public void setCorrectTrueFalse(Boolean tf) { this.correctTrueFalse = tf; }

    public String getCorrectShortText() { return correctShortText; }
    public void setCorrectShortText(String st) { this.correctShortText = st; }
}

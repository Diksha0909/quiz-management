package com.example.quiz.dto;
// DTO to add a choice to a question
public class AddChoiceRequest {
    private String text;
    private Boolean correct;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Boolean getCorrect() { return correct; }
    public void setCorrect(Boolean correct) { this.correct = correct; }
}

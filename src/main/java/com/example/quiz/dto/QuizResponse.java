package com.example.quiz.dto;

import java.util.List;

public class QuizResponse {

    private Long id;
    private String title;
    private List<QuestionDto> questions;

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<QuestionDto> getQuestions() { return questions; }
    public void setQuestions(List<QuestionDto> questions) { this.questions = questions; }

    // ----------------------------------------------------------
    // INNER CLASS: Question DTO
    // ----------------------------------------------------------
    public static class QuestionDto {
        private Long id;
        private String text;
        private String type; // MCQ, TRUE_FALSE, SHORT_TEXT
        private List<ChoiceDto> choices; // Only for MCQ

        private Boolean correctTrueFalse; // For T/F questions
        private String correctShortText;  // For short text questions

        // --- getters & setters ---
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public List<ChoiceDto> getChoices() { return choices; }
        public void setChoices(List<ChoiceDto> choices) { this.choices = choices; }

        public Boolean getCorrectTrueFalse() { return correctTrueFalse; }
        public void setCorrectTrueFalse(Boolean correctTrueFalse) { this.correctTrueFalse = correctTrueFalse; }

        public String getCorrectShortText() { return correctShortText; }
        public void setCorrectShortText(String correctShortText) { this.correctShortText = correctShortText; }
    }

    // ----------------------------------------------------------
    // INNER CLASS: Choice DTO
    // ----------------------------------------------------------
    public static class ChoiceDto {
        private Long id;
        private String text;
        private boolean correct;

        // --- getters & setters ---
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }

        public boolean isCorrect() { return correct; }
        public void setCorrect(boolean correct) { this.correct = correct; }
    }
}

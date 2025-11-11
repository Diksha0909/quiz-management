package com.example.quiz.dto;

import java.util.List;

// DTO to submit a quiz
public class SubmitQuizRequest {

    private List<AnswerDto> answers;

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }

    public static class AnswerDto {
        private Long questionId;
        private Long choiceId;     // MCQ
        private Boolean value;     // True/False
        private String text;       // Short text

        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }

        public Long getChoiceId() { return choiceId; }
        public void setChoiceId(Long choiceId) { this.choiceId = choiceId; }

        public Boolean getValue() { return value; }
        public void setValue(Boolean value) { this.value = value; }

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }
}

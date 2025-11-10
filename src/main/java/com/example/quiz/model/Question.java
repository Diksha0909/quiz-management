package com.example.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name = "questions")
public class Question {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "quiz_id")
 @JsonIgnore
 private Quiz quiz;

 @Enumerated(EnumType.STRING)
 private QuestionType type = QuestionType.MCQ;

 @NotBlank
 private String text;

 private Boolean correctTrueFalse;
 private String correctShortText;

 // ✅ Choices auto delete
 @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
 @OrderBy("id ASC")
 private List<Choice> choices = new ArrayList<>();

 // ✅ Answers auto delete
 @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
 @JsonIgnore
 private List<Answer> answers = new ArrayList<>();

 // Getters / Setters
 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }

 public Quiz getQuiz() { return quiz; }
 public void setQuiz(Quiz quiz) { this.quiz = quiz; }

 public QuestionType getType() { return type; }
 public void setType(QuestionType type) { this.type = type; }

 public String getText() { return text; }
 public void setText(String text) { this.text = text; }

 public Boolean getCorrectTrueFalse() { return correctTrueFalse; }
 public void setCorrectTrueFalse(Boolean v) { this.correctTrueFalse = v; }

 public String getCorrectShortText() { return correctShortText; }
 public void setCorrectShortText(String v) { this.correctShortText = v; }

 public List<Choice> getChoices() { return choices; }
 public void setChoices(List<Choice> choices) { this.choices = choices; }

 public List<Answer> getAnswers() { return answers; }
 public void setAnswers(List<Answer> answers) { this.answers = answers; }
}

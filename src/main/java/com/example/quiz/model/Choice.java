package com.example.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "choices")
public class Choice {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "question_id")
 @JsonIgnore
 private Question question;

 private String text;
 private boolean correct;

 // Getters / Setters
 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }

 public Question getQuestion() { return question; }
 public void setQuestion(Question question) { this.question = question; }

 public String getText() { return text; }
 public void setText(String text) { this.text = text; }

 public boolean isCorrect() { return correct; }
 public void setCorrect(boolean correct) { this.correct = correct; }
}

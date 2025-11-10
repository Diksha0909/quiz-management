package com.example.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 // ✅ Prevent recursion
 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "submission_id")
 @JsonIgnore
 private Submission submission;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "question_id")
 @JsonIgnore
 private Question question;

 private Long chosenChoiceId;
 private Boolean chosenTrueFalse;
 private String shortText;
 private boolean correct;

 // Getters / Setters
 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }

 public Submission getSubmission() { return submission; }
 public void setSubmission(Submission submission) { this.submission = submission; }

 public Question getQuestion() { return question; }
 public void setQuestion(Question question) { this.question = question; }

 public Long getChosenChoiceId() { return chosenChoiceId; }
 public void setChosenChoiceId(Long chosenChoiceId) { this.chosenChoiceId = chosenChoiceId; }

 public Boolean getChosenTrueFalse() { return chosenTrueFalse; }
 public void setChosenTrueFalse(Boolean chosenTrueFalse) { this.chosenTrueFalse = chosenTrueFalse; }

 public String getShortText() { return shortText; }
 public void setShortText(String shortText) { this.shortText = shortText; }

 public boolean isCorrect() { return correct; }
 public void setCorrect(boolean correct) { this.correct = correct; }
}

package com.example.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "submissions")
public class Submission {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "quiz_id")
 @JsonIgnore
 private Quiz quiz;

 private int score;
 private int total;

 // ✅ All answers for this submission delete automatically
 @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
 private List<Answer> answers = new ArrayList<>();

 // Getters / Setters
 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }

 public Quiz getQuiz() { return quiz; }
 public void setQuiz(Quiz quiz) { this.quiz = quiz; }

 public int getScore() { return score; }
 public void setScore(int score) { this.score = score; }

 public int getTotal() { return total; }
 public void setTotal(int total) { this.total = total; }

 public List<Answer> getAnswers() { return answers; }
 public void setAnswers(List<Answer> answers) { this.answers = answers; }
}

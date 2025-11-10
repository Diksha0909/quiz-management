package com.example.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name = "quizzes")
public class Quiz {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @NotBlank
 private String title;

 // ✅ Questions delete automatically
 @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
 @OrderBy("id ASC")
 private List<Question> questions = new ArrayList<>();

 // ✅ Submissions delete automatically
 @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
 @JsonIgnore
 private List<Submission> submissions = new ArrayList<>();

 // Getters / Setters
 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }

 public String getTitle() { return title; }
 public void setTitle(String title) { this.title = title; }

 public List<Question> getQuestions() { return questions; }
 public void setQuestions(List<Question> questions) { this.questions = questions; }

 public List<Submission> getSubmissions() { return submissions; }
 public void setSubmissions(List<Submission> submissions) { this.submissions = submissions; }
}

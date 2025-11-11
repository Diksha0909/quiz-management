package com.example.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"quiz", "choices", "answers"})
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

 @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
 @OrderBy("id ASC")
 private List<Choice> choices = new ArrayList<>();

 @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
 @JsonIgnore
 private List<Answer> answers = new ArrayList<>();
}

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
@Table(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"questions", "submissions"})
public class Quiz {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @NotBlank
 private String title;

 @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
 @OrderBy("id ASC")
 private List<Question> questions = new ArrayList<>();

 @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
 @JsonIgnore
 private List<Submission> submissions = new ArrayList<>();
}

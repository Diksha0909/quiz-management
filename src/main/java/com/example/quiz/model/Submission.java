package com.example.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"quiz", "answers"})
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

 @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
 private List<Answer> answers = new ArrayList<>();
}

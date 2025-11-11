package com.example.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"submission", "question"})
public class Answer {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

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
}

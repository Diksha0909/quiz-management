package com.example.quiz.repo;
import com.example.quiz.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for managing Answer entities.
public interface AnswerRepository extends JpaRepository<Answer, Long> {}
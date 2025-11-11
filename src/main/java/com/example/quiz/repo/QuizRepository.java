package com.example.quiz.repo;
import com.example.quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for managing Quiz entities.
public interface QuizRepository extends JpaRepository<Quiz, Long> {}
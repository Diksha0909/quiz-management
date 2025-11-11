package com.example.quiz.repo;
import com.example.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for managing Question entities.
public interface QuestionRepository extends JpaRepository<Question, Long> {}
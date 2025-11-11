package com.example.quiz.repo;
import com.example.quiz.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for managing Choice entities.
public interface ChoiceRepository extends JpaRepository<Choice, Long> {}
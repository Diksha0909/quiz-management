package com.example.quiz.repo;
import com.example.quiz.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for managing Submission entities.
public interface SubmissionRepository extends JpaRepository<Submission, Long> {}
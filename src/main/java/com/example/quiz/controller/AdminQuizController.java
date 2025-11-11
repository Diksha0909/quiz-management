package com.example.quiz.controller;

import com.example.quiz.config.AdminConfig;
import com.example.quiz.dto.*;
import com.example.quiz.service.QuizService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminQuizController {

    private final QuizService svc;
    private final AdminConfig adminConfig;

    public AdminQuizController(QuizService svc, AdminConfig  adminConfig)
    {
        this.svc = svc;
        this.adminConfig = adminConfig;
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        if (adminConfig.getAdminUsername().equals(username) &&
                adminConfig.getAdminPassword().equals(password)) {

            return ResponseEntity.ok("OK");
        }

        return new ResponseEntity<>("Access Denied", HttpStatus.UNAUTHORIZED);
    }

    // List all quizzes for admin dashboard
    @GetMapping("/quizzes")
    public Object adminQuizList() {
        return svc.adminQuizList();
    }

    // Create a new quiz
    @PostMapping("/quizzes")
    @ResponseStatus(HttpStatus.CREATED)
    public Object createQuiz(@RequestBody CreateQuizRequest req) {
        return svc.createQuiz(req.getTitle());
    }

    // View a quiz with all its questions/choices
    @GetMapping("/quizzes/{id}")
    public Object adminQuiz(@PathVariable Long id) {
        return svc.adminQuiz(id);
    }

    // Rename an existing quiz
    @PatchMapping("/quizzes/{id}")
    public Object renameQuiz(@PathVariable Long id,
                             @RequestBody RenameQuizRequest req) {
        return svc.renameQuiz(id, req.getTitle());
    }

    // Delete a quiz
    @DeleteMapping("/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable Long id) {
        svc.deleteQuiz(id);
    }


    // Add a question to a quiz
    @PostMapping("/quizzes/{quizId}/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addQuestion(@PathVariable Long quizId,
                              @RequestBody AddQuestionRequest req) {

        return svc.addQuestion(
                quizId,
                req.getType(),
                req.getText(),
                req.getCorrectTrueFalse(),
                req.getCorrectShortText()
        );
    }

    // Update question text/type/correct answers
    @PatchMapping("/questions/{id}")
    public Object updateQuestion(@PathVariable Long id,
                                 @RequestBody UpdateQuestionRequest req) {

        return svc.updateQuestion(
                id,
                req.getText(),
                req.getType(),
                req.getCorrectTrueFalse(),
                req.getCorrectShortText()
        );
    }

    // Delete a question
    @DeleteMapping("/questions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable Long id) {
        svc.removeQuestion(id);
    }

    // Add a choice to a question
    @PostMapping("/questions/{questionId}/choices")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addChoice(@PathVariable Long questionId,
                            @RequestBody AddChoiceRequest req) {

        return svc.addChoice(
                questionId,
                req.getText(),
                Boolean.TRUE.equals(req.getCorrect())
        );
    }

    // Update a choice
    @PatchMapping("/choices/{id}")
    public Object updateChoice(@PathVariable Long id,
                               @RequestBody UpdateChoiceRequest req) {

        return svc.updateChoice(
                id,
                req.getText(),
                req.getCorrect()
        );
    }

    // Delete a choice
    @DeleteMapping("/choices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChoice(@PathVariable Long id) {
        svc.removeChoice(id);
    }
}

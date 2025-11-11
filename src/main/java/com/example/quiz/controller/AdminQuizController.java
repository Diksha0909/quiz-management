package com.example.quiz.controller;

import com.example.quiz.model.QuestionType;
import com.example.quiz.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminQuizController {

    private final QuizService svc;

    public AdminQuizController(QuizService svc) {
        this.svc = svc;
    }

    // Return list of all quizzes for the admin panel
    @GetMapping("/quizzes")
    public List<Map<String, Object>> adminQuizList() {
        return svc.adminQuizList();
    }

    // Create a new quiz with the given title
    @PostMapping("/quizzes")
    @ResponseStatus(HttpStatus.CREATED)
    public Object createQuiz(@RequestBody Map<String, String> body) {
        return svc.createQuiz(body.get("title"));
    }

    // Fetch quiz details (questions, choices) for editing
    @GetMapping("/quizzes/{id}")
    public Object adminQuiz(@PathVariable("id") Long id) {
        return svc.adminQuiz(id);
    }

    // Rename an existing quiz
    @PatchMapping("/quizzes/{id}")
    public Object renameQuiz(@PathVariable("id") Long id,
                             @RequestBody Map<String, String> body) {
        return svc.renameQuiz(id, body.get("title"));
    }

    // Delete a quiz
    @DeleteMapping("/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable("id") Long id) {
        svc.deleteQuiz(id);
    }

    // Add a question to a quiz
    @PostMapping("/quizzes/{quizId}/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addQuestion(@PathVariable("quizId") Long quizId,
                              @RequestBody Map<String, Object> body) {

        QuestionType type = QuestionType.valueOf(((String) body.get("type")).toUpperCase());
        String text = (String) body.get("text");
        Boolean tf = (Boolean) body.get("correctTrueFalse");
        String st = (String) body.get("correctShortText");

        return svc.addQuestion(quizId, type, text, tf, st);
    }

    // Update a question’s text, type, or correct answer
    @PatchMapping("/questions/{id}")
    public Object updateQuestion(@PathVariable("id") Long id,
                                 @RequestBody Map<String, Object> body) {

        String text = (String) body.get("text");
        QuestionType type = body.get("type") == null ? null :
                QuestionType.valueOf(((String) body.get("type")).toUpperCase());
        Boolean tf = (Boolean) body.get("correctTrueFalse");
        String st = (String) body.get("correctShortText");

        return svc.updateQuestion(id, text, type, tf, st);
    }

    // Delete a question
    @DeleteMapping("/questions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable("id") Long id) {
        svc.removeQuestion(id);
    }

    // Add a choice option to a multiple-choice question
    @PostMapping("/questions/{questionId}/choices")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addChoice(@PathVariable("questionId") Long qid,
                            @RequestBody Map<String, Object> body) {

        String text = (String) body.get("text");
        boolean correct = body.get("correct") != null && (Boolean) body.get("correct");

        return svc.addChoice(qid, text, correct);
    }

    // Update a choice’s text or correct flag
    @PatchMapping("/choices/{id}")
    public Object updateChoice(@PathVariable("id") Long id,
                               @RequestBody Map<String, Object> body) {

        String text = (String) body.get("text");
        Boolean correct = (Boolean) body.get("correct");

        return svc.updateChoice(id, text, correct);
    }

    // Delete a choice option
    @DeleteMapping("/choices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChoice(@PathVariable("id") Long id) {
        svc.removeChoice(id);
    }
}

package com.example.quiz.controller;

import com.example.quiz.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicQuizController {

    private final QuizService svc;

    public PublicQuizController(QuizService svc) {
        this.svc = svc;
    }

    // Return list of quizzes available for the public to take
    @GetMapping("/quizzes")
    public List<Map<String, Object>> publicQuizzes() {
        return svc.publicQuizzes();
    }

    // Fetch a single quiz with its questions for taking
    @GetMapping("/quizzes/{id}")
    public Map<String, Object> publicQuiz(@PathVariable("id") Long id) {
        return svc.publicQuiz(id);
    }

    // Submit quiz answers and return evaluation (score or correctness)
    @PostMapping("/quizzes/{id}/submit")
    public Map<String, Object> submit(@PathVariable("id") Long id,
                                      @RequestBody Map<String, Object> body) {

        List<Map<String, Object>> answers =
                (List<Map<String, Object>>) body.get("answers");

        return svc.submit(id, answers);
    }
}

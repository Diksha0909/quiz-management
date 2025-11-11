package com.example.quiz.controller;

import com.example.quiz.dto.SubmitQuizRequest;
import com.example.quiz.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicQuizController {

    private final QuizService svc;

    public PublicQuizController(QuizService svc) {
        this.svc = svc;
    }

    // List public quizzes
    @GetMapping("/quizzes")
    public List<Map<String, Object>> publicQuizzes() {
        return svc.publicQuizzes();
    }

    // Get quiz by id for public taking
    @GetMapping("/quizzes/{id}")
    public Map<String, Object> publicQuiz(@PathVariable Long id) {
        return svc.publicQuiz(id);
    }

    // Submit answers
    @PostMapping("/quizzes/{id}/submit")
    public Map<String, Object> submit(@PathVariable Long id,
                                      @RequestBody SubmitQuizRequest body) {

        // Convert DTOs → List<Map<String,Object>>
        List<Map<String, Object>> answerMaps = body.getAnswers().stream()
                .map(a -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("questionId", a.getQuestionId());
                    m.put("choiceId", a.getChoiceId());
                    m.put("value", a.getValue());
                    m.put("text", a.getText());
                    return m;
                })
                .toList();

        return svc.submit(id, answerMaps);
    }

}

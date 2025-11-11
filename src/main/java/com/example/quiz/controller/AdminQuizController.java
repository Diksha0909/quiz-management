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

    // ================= QUIZ =================

    @GetMapping("/quizzes")
    public List<Map<String, Object>> adminQuizList() {
        return svc.adminQuizList();
    }

    @PostMapping("/quizzes")
    @ResponseStatus(HttpStatus.CREATED)
    public Object createQuiz(@RequestBody Map<String, String> body) {
        return svc.createQuiz(body.get("title"));
    }

    @GetMapping("/quizzes/{id}")
    public Object adminQuiz(@PathVariable("id") Long id) {
        return svc.adminQuiz(id);
    }

    @PatchMapping("/quizzes/{id}")
    public Object renameQuiz(@PathVariable("id") Long id,
                             @RequestBody Map<String, String> body) {
        return svc.renameQuiz(id, body.get("title"));
    }

    @DeleteMapping("/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable("id") Long id) {
        svc.deleteQuiz(id);
    }


    // ================= QUESTIONS =================

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

    @DeleteMapping("/questions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable("id") Long id) {
        svc.removeQuestion(id);
    }


    // ================= CHOICES =================

    @PostMapping("/questions/{questionId}/choices")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addChoice(@PathVariable("questionId") Long qid,
                            @RequestBody Map<String, Object> body) {

        String text = (String) body.get("text");
        boolean correct = body.get("correct") != null && (Boolean) body.get("correct");

        return svc.addChoice(qid, text, correct);
    }

    @PatchMapping("/choices/{id}")
    public Object updateChoice(@PathVariable("id") Long id,
                               @RequestBody Map<String, Object> body) {

        String text = (String) body.get("text");
        Boolean correct = (Boolean) body.get("correct");

        return svc.updateChoice(id, text, correct);
    }

    @DeleteMapping("/choices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChoice(@PathVariable("id") Long id) {
        svc.removeChoice(id);
    }
}

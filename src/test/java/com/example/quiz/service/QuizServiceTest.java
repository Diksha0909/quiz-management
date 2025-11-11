package com.example.quiz.service;

import com.example.quiz.model.*;
import com.example.quiz.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    private QuizRepository quizRepo;
    private QuestionRepository questionRepo;
    private ChoiceRepository choiceRepo;
    private SubmissionRepository submissionRepo;
    private AnswerRepository answerRepo;

    private QuizService service;

    @BeforeEach
    void setup() {
        quizRepo = mock(QuizRepository.class);
        questionRepo = mock(QuestionRepository.class);
        choiceRepo = mock(ChoiceRepository.class);
        submissionRepo = mock(SubmissionRepository.class);
        answerRepo = mock(AnswerRepository.class);

        service = new QuizService(quizRepo, questionRepo, choiceRepo, submissionRepo, answerRepo);
    }

    // ======================
    // QUIZ CRUD
    // ======================
    @Test
    void createQuiz_shouldPersistQuiz() {
        Quiz q = new Quiz();
        q.setId(1L);
        q.setTitle("Sample Quiz");

        when(quizRepo.save(any())).thenReturn(q);

        Quiz created = service.createQuiz("Sample Quiz");

        assertEquals(1L, created.getId());
        assertEquals("Sample Quiz", created.getTitle());
        verify(quizRepo).save(any());
    }

    @Test
    void getQuiz_shouldReturnQuiz() {
        Quiz quiz = new Quiz();
        quiz.setId(5L);

        when(quizRepo.findById(5L)).thenReturn(Optional.of(quiz));

        Quiz fetched = service.getQuiz(5L);
        assertEquals(5L, fetched.getId());
    }

    @Test
    void getQuiz_shouldFailWhenNotFound() {
        when(quizRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.getQuiz(99L));
    }

    @Test
    void renameQuiz_shouldUpdateTitle() {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Old");

        when(quizRepo.findById(1L)).thenReturn(Optional.of(quiz));
        when(quizRepo.save(any())).thenReturn(quiz);

        Quiz updated = service.renameQuiz(1L, "New Title");
        assertEquals("New Title", updated.getTitle());
    }

    @Test
    void deleteQuiz_shouldCallRepoDelete() {
        service.deleteQuiz(10L);
        verify(quizRepo).deleteById(10L);
    }

    // ======================
    // QUESTION CREATION
    // ======================
    @Test
    void addQuestion_shouldAttachToQuiz() {
        Quiz quiz = new Quiz();
        quiz.setId(1L);

        when(quizRepo.findById(1L)).thenReturn(Optional.of(quiz));

        Question saved = new Question();
        saved.setId(101L);

        when(questionRepo.save(any())).thenReturn(saved);

        Question q = service.addQuestion(1L, QuestionType.TRUE_FALSE, "Is Earth round?", true, null);

        assertEquals(101L, q.getId());
        verify(questionRepo).save(any());
    }

    @Test
    void updateQuestion_shouldModifyFields() {
        Question q = new Question();
        q.setId(10L);
        q.setText("Old text");
        q.setType(QuestionType.TRUE_FALSE);

        when(questionRepo.findById(10L)).thenReturn(Optional.of(q));
        when(questionRepo.save(any())).thenReturn(q);

        Question updated = service.updateQuestion(10L, "New text", QuestionType.SHORT_TEXT, null, "Answer");

        assertEquals("New text", updated.getText());
        assertEquals(QuestionType.SHORT_TEXT, updated.getType());
        assertEquals("Answer", updated.getCorrectShortText());
    }

    @Test
    void removeQuestion_shouldDeleteById() {
        service.removeQuestion(7L);
        verify(questionRepo).deleteById(7L);
    }

    // ======================
    // CHOICES
    // ======================
    @Test
    void addChoice_shouldAttachToQuestion() {
        Question q = new Question();
        q.setId(20L);

        when(questionRepo.findById(20L)).thenReturn(Optional.of(q));

        Choice saved = new Choice();
        saved.setId(200L);

        when(choiceRepo.save(any())).thenReturn(saved);

        Choice c = service.addChoice(20L, "Option A", true);

        assertEquals(200L, c.getId());
        verify(choiceRepo).save(any());
    }

    @Test
    void updateChoice_shouldModifyFields() {
        Choice c = new Choice();
        c.setId(55L);
        c.setText("Old");
        c.setCorrect(false);

        when(choiceRepo.findById(55L)).thenReturn(Optional.of(c));
        when(choiceRepo.save(any())).thenReturn(c);

        Choice updated = service.updateChoice(55L, "New", true);

        assertEquals("New", updated.getText());
        assertTrue(updated.isCorrect());
    }

    @Test
    void removeChoice_shouldDeleteById() {
        service.removeChoice(123L);
        verify(choiceRepo).deleteById(123L);
    }

    // ======================
    // PUBLIC QUIZZES
    // ======================
    @Test
    void publicQuizzes_shouldReturnOnlyQuizzesWithQuestions() {
        Quiz emptyQuiz = new Quiz();
        emptyQuiz.setId(1L);
        emptyQuiz.setQuestions(Collections.emptyList());

        Quiz quiz = new Quiz();
        quiz.setId(2L);
        quiz.setQuestions(List.of(new Question()));

        when(quizRepo.findAll()).thenReturn(List.of(emptyQuiz, quiz));

        List<Map<String, Object>> result = service.publicQuizzes();

        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).get("id"));
    }

    // ======================
    // SUBMISSION LOGIC
    // ======================
    @Test
    void submit_shouldCalculateScoreForMCQ() {
        // Quiz setup
        Quiz quiz = new Quiz();
        quiz.setId(1L);

        Question q = new Question();
        q.setId(10L);
        q.setType(QuestionType.MCQ);

        Choice correct = new Choice();
        correct.setId(100L);
        correct.setCorrect(true);
        correct.setText("Correct");

        Choice wrong = new Choice();
        wrong.setId(101L);
        wrong.setCorrect(false);

        q.setChoices(List.of(correct, wrong));
        quiz.setQuestions(List.of(q));

        when(quizRepo.findById(1L)).thenReturn(Optional.of(quiz));

        Submission savedSub = new Submission();
        savedSub.setId(500L);

        when(submissionRepo.save(any())).thenReturn(savedSub);

        Map<String, Object> answer = Map.of(
                "questionId", 10,
                "choiceId", 100
        );

        Map<String, Object> result = service.submit(1L, List.of(answer));

        assertEquals(1, result.get("score"));
        assertEquals(1, result.get("total"));
    }
}

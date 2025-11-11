package com.example.quiz.service;
import com.example.quiz.model.*; import com.example.quiz.repo.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.*; import java.util.stream.*;
@Service @Transactional
public class QuizService {


 private final QuizRepository quizRepo; private final QuestionRepository questionRepo; private final ChoiceRepository choiceRepo; private final SubmissionRepository submissionRepo; private final AnswerRepository answerRepo;
 public QuizService(QuizRepository a, QuestionRepository b, ChoiceRepository c, SubmissionRepository d, AnswerRepository e){this.quizRepo=a; this.questionRepo=b; this.choiceRepo=c; this.submissionRepo=d; this.answerRepo=e;}
 public List<Quiz> allQuizzes(){return quizRepo.findAll();}

 public List<Map<String,Object>> adminQuizList() {
  return quizRepo.findAll().stream().map(q -> {
   Map<String,Object> m = new LinkedHashMap<>();
   m.put("id", q.getId());
   m.put("title", q.getTitle());
   return m;
  }).toList();
 }

 public Quiz getQuiz(Long id){return quizRepo.findById(id).orElseThrow(()->new NoSuchElementException("Quiz not found: "+id));}
 public Quiz createQuiz(String title){Quiz q=new Quiz(); q.setTitle(title); return quizRepo.save(q);}
 public Quiz renameQuiz(Long id, String title){Quiz q=getQuiz(id); q.setTitle(title); return quizRepo.save(q);}
 public void deleteQuiz(Long id){quizRepo.deleteById(id);}
 public Map<String,Object> adminQuiz(Long id){
  Quiz quiz=getQuiz(id); Map<String,Object> dto=new LinkedHashMap<>(); dto.put("id",quiz.getId()); dto.put("title",quiz.getTitle());
  List<Map<String,Object>> qs=quiz.getQuestions().stream().map(q->{ Map<String,Object> m=new LinkedHashMap<>(); m.put("id",q.getId()); m.put("type",q.getType().name()); m.put("text",q.getText()); m.put("correctTrueFalse",q.getCorrectTrueFalse()); m.put("correctShortText",q.getCorrectShortText());
   if(q.getType()==QuestionType.MCQ){ m.put("choices", q.getChoices().stream().map(c->{ Map<String,Object> cm=new LinkedHashMap<>(); cm.put("id",c.getId()); cm.put("text",c.getText()); cm.put("correct",c.isCorrect()); return cm;}).toList()); }
   return m; }).toList(); dto.put("questions",qs); return dto; }
 public List<Map<String,Object>> publicQuizzes() {
  return quizRepo.findAll().stream()
          .filter(q -> q.getQuestions() != null && !q.getQuestions().isEmpty())  // ✅ Only quizzes WITH questions
          .map(q -> {
           Map<String,Object> m = new LinkedHashMap<>();
           m.put("id", q.getId());
           m.put("title", q.getTitle());
           return m;
          })
          .toList();
 }

 public Map<String,Object> publicQuiz(Long id){
  Quiz quiz=getQuiz(id); Map<String,Object> dto=new LinkedHashMap<>(); dto.put("id",quiz.getId()); dto.put("title",quiz.getTitle());
  List<Map<String,Object>> qs=quiz.getQuestions().stream().map(q->{ Map<String,Object> m=new LinkedHashMap<>(); m.put("id",q.getId()); m.put("type",q.getType().name()); m.put("text",q.getText());
   if(q.getType()==QuestionType.MCQ){ m.put("choices", q.getChoices().stream().map(c->Map.of("id",c.getId(),"text",c.getText())).toList()); }
   return m; }).toList(); dto.put("questions",qs); return dto; }
 public Question addQuestion(Long quizId, QuestionType type, String text, Boolean tf, String st){ Quiz quiz=getQuiz(quizId); Question q=new Question(); q.setQuiz(quiz); q.setType(type); q.setText(text); q.setCorrectTrueFalse(tf); q.setCorrectShortText(st); return questionRepo.save(q); }
 public Question updateQuestion(Long id, String text, QuestionType type, Boolean tf, String st){ Question q=questionRepo.findById(id).orElseThrow(()->new NoSuchElementException("Question not found: "+id)); if(text!=null) q.setText(text); if(type!=null) q.setType(type); if(tf!=null) q.setCorrectTrueFalse(tf); if(st!=null) q.setCorrectShortText(st); return questionRepo.save(q); }
 public void removeQuestion(Long id){ questionRepo.deleteById(id); }
 public Choice addChoice(Long qid, String text, boolean correct){ Question q=questionRepo.findById(qid).orElseThrow(()->new NoSuchElementException("Question not found: "+qid)); Choice c=new Choice(); c.setQuestion(q); c.setText(text); c.setCorrect(correct); return choiceRepo.save(c); }
 public Choice updateChoice(Long id, String text, Boolean correct){ Choice c=choiceRepo.findById(id).orElseThrow(()->new NoSuchElementException("Choice not found: "+id)); if(text!=null) c.setText(text); if(correct!=null) c.setCorrect(correct); return choiceRepo.save(c); }
 public void removeChoice(Long id){ choiceRepo.deleteById(id); }
 public Map<String,Object> submit(Long quizId, List<Map<String,Object>> answers){ Quiz quiz=getQuiz(quizId); Submission sub=new Submission(); sub.setQuiz(quiz); sub=submissionRepo.save(sub);
  int score=0, total=quiz.getQuestions().size(); List<Map<String,Object>> feedback=new ArrayList<>();
  Map<Long,Question> qMap=quiz.getQuestions().stream().collect(Collectors.toMap(Question::getId, q->q));
  for(Map<String,Object> ap: answers){ Long qid=((Number)ap.get("questionId")).longValue(); Question q=qMap.get(qid); if(q==null) continue;
   Answer a=new Answer(); a.setSubmission(sub); a.setQuestion(q); boolean correct=false; String correctText=""; String yourText="";
   switch(q.getType()){ case MCQ -> { Long chosen=ap.get("choiceId")==null?null:((Number)ap.get("choiceId")).longValue(); a.setChosenChoiceId(chosen);
     Optional<Choice> cc=q.getChoices().stream().filter(Choice::isCorrect).findFirst(); correct=chosen!=null && cc.isPresent() && chosen.equals(cc.get().getId());
     correctText=cc.map(Choice::getText).orElse(""); yourText=q.getChoices().stream().filter(c->Objects.equals(c.getId(),chosen)).map(Choice::getText).findFirst().orElse(""); }
     case TRUE_FALSE -> { Boolean val=(Boolean)ap.get("value"); a.setChosenTrueFalse(val); correct=q.getCorrectTrueFalse()!=null && Objects.equals(q.getCorrectTrueFalse(),val);
       correctText=String.valueOf(q.getCorrectTrueFalse()); yourText=String.valueOf(val); }
     case SHORT_TEXT -> { String txt=(String)ap.get("text"); a.setShortText(txt); String exp=q.getCorrectShortText()==null?"":q.getCorrectShortText();
       correct=txt!=null && txt.trim().equalsIgnoreCase(exp.trim()); correctText=exp; yourText=txt==null?"":txt; } }
   a.setCorrect(correct); answerRepo.save(a); if(correct) score++;
   Map<String,Object> fb=new LinkedHashMap<>(); fb.put("questionId", q.getId()); fb.put("question", q.getText()); fb.put("type", q.getType().name()); fb.put("correct", correct); fb.put("yourAnswer", yourText); fb.put("correctAnswer", correctText); feedback.add(fb);
  }
  sub.setScore(score); sub.setTotal(total); submissionRepo.save(sub);
  Map<String,Object> res=new LinkedHashMap<>(); res.put("submissionId", sub.getId()); res.put("quizId", quiz.getId()); res.put("title", quiz.getTitle()); res.put("score", score); res.put("total", total); res.put("feedback", feedback); return res;
 }
}
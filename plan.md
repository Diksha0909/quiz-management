# Quiz Management System — Plan & Architecture

This document outlines the assumptions, scope, and high-level design decisions for the Quiz Management System built using **Spring Boot (Java 17)**, **MySQL**, **HTML/JS frontend**, and a clean Admin/Public separation.  
The goal is to keep the implementation simple, production-ready, and easy to extend later (e.g., authentication, publishing workflow, reporting).

---

## 1. Assumptions

1. The system has **two types of users**:
    - **Admin** — can create, edit, and manage quizzes, questions, and choices.
    - **Public Users** — can view available quizzes and submit answers.
    - No authentication has been implemented yet; admin access is open.

2. A quiz is only visible publicly **if it contains at least one question**.

3. All quizzes, questions, choices, submissions, and answers are stored in a MySQL database.

4. The admin panel is intentionally simple:
    - Basic HTML pages
    - Vanilla JavaScript + fetch API
    - No UI frameworks, consistent with requirement (Option A: simple HTML UI)

5. The system should clean up related data automatically:
    - Deleting a Quiz removes its Questions, Choices, Submissions, and Answers.
    - Deleting a Question removes its Choices and Answers.
    - This is handled with JPA cascading + orphan removal.

---

## 2. Project Scope

### Admin Features
- Create quiz with a title.
- View all quizzes.
- Rename or delete quizzes.
- Add questions to a quiz:
    - MCQ (multiple choice)
    - True/False
    - Short text answer
- Add choices for MCQ questions.
- Mark correct answers.
- Edit or delete questions.
- Edit or delete choices.
- Auto-cleanup when items are removed.

### Public Features
- List all quizzes that have at least one question.
- View quiz details and questions.
- Submit answers.
- Receive score and question-level feedback.

### Out of Scope (for now)
- Authentication / login.
- Quiz publish/unpublish workflow.
- Timer, paging, or advanced UI components.
- Statistics/reporting dashboards.
- User accounts or quiz attempts history.

These may be added later.

---

## 3. High-Level Architecture

### Layers
1. **Controller Layer**
    - `AdminQuizController` → `/api/admin/**`
    - `PublicQuizController` → `/api/public/**`

2. **Service Layer**
    - `QuizService` handles all business logic, validation, and data access orchestration.

3. **Repository Layer**
    - Standard Spring Data JPA repositories for all entities.

4. **Frontend Layer**
    - HTML pages served from `src/main/resources/static`
    - JavaScript files handle admin interactions and public quiz flow
    - Pages:
        - `admin.html`
        - `questions.html`
        - `public.html`
        - `quiz-take.html`

5. **Database Layer**
    - MySQL database with five main tables and relationships.

---

## 4. Database Schema

### Entities

#### Quiz
| Field | Type | Notes |
|-------|-------|--------|
| id | BIGINT | PK |
| title | VARCHAR | Not blank |
| questions | One-to-many | Cascade + orphanRemoval |
| submissions | One-to-many | Cascade + orphanRemoval |

#### Question
| Field | Type | Notes |
|-------|-------|--------|
| id | BIGINT | PK |
| quiz_id | BIGINT | FK to Quiz |
| type | ENUM (MCQ, TRUE_FALSE, SHORT_TEXT) |
| text | VARCHAR | Required |
| correctTrueFalse | BOOLEAN | For TRUE_FALSE questions |
| correctShortText | VARCHAR | For text answers |
| choices | One-to-many | Cascade + orphanRemoval |
| answers | One-to-many | Cascade + orphanRemoval |

#### Choice
| Field | Type | Notes |
|-------|-------|--------|
| id | BIGINT | PK |
| question_id | BIGINT | FK |
| text | VARCHAR | Answer text |
| correct | BOOLEAN | Marks the correct option |

#### Submission
| Field | Type | Notes |
|-------|-------|--------|
| id | BIGINT | PK |
| quiz_id | BIGINT | FK |
| score | INT | Computed |
| total | INT | Number of questions in quiz |
| answers | One-to-many | Cascade + orphanRemoval |

#### Answer
| Field | Type | Notes |
|-------|-------|--------|
| id | BIGINT | PK |
| submission_id | BIGINT | FK |
| question_id | BIGINT | FK |
| chosenChoiceId | BIGINT | MCQ choice ID |
| chosenTrueFalse | BOOLEAN | |
| shortText | VARCHAR | |
| correct | BOOLEAN | Computed |

---

## 5. API Overview

### Admin APIs (`/api/admin/...`)
- `GET /quizzes`
- `POST /quizzes`
- `GET /quizzes/{id}`
- `PATCH /quizzes/{id}`
- `DELETE /quizzes/{id}`
- `POST /quizzes/{quizId}/questions`
- `PATCH /questions/{id}`
- `DELETE /questions/{id}`
- `POST /questions/{questionId}/choices`
- `PATCH /choices/{id}`
- `DELETE /choices/{id}`

### Public APIs (`/api/public/...`)
- `GET /quizzes`
- `GET /quizzes/{id}`
- `POST /quizzes/{id}/submit`

---

## 6. Key Design Decisions

1. **Separated Admin & Public Controllers**  
   Prevents accidental mixing and prepares for future authentication.

2. **Avoided exposing JPA entities directly**  
   Admin/public endpoints return DTO-like maps to control JSON payload shape.

3. **Cascade Deletes Implemented Properly**  
   Ensures referential integrity without manual cleanup.

4. **Public Quiz Visibility Rule**  
   A quiz must have at least one question to appear on the public page.

5. **Minimalist Frontend**  
   Simple HTML + vanilla JS, easy to test and adapt.

---

## 7. Future Improvements (Optional)

- Add authentication for admin routes.
- Add publish/draft mode for quizzes.
- Add pagination and quiz search.
- Add exporting quiz results.
- Add timed quizzes or attempt limits.
- Add proper DTO classes instead of Map responses.

---

## 8. Current Status

The system is:

- Functional end-to-end
- Deployed-ready
- Cleanly structured
- Stable with correct cascading rules
- Uses predictable API contracts
- Frontend is simple but works well for admin + public flows

---

# Quiz Management System

A simple Quiz Management System built with **Spring Boot (Java 17)**, **MySQL**, **JPA/Hibernate**, and a **vanilla HTML/JS frontend**.  
The application provides separate interfaces for **Admin** (quiz creation & management) and **Public Users** (taking quizzes).

---

## ✅ Features

### **Admin**
- Create quizzes
- Add MCQ, True/False, or Short Text questions
- Add choices for MCQ questions
- Mark correct answers
- Edit or delete questions
- Edit or delete choices
- Rename or delete quizzes
- Automatic cleanup (cascade delete)

### **Public Users**
- View list of available quizzes (only quizzes with questions)
- Take quizzes
- Submit answers
- View score and question-level feedback

---

## ✅ Technology Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA + Hibernate**
- **MySQL**
- **HTML, CSS, JavaScript (Fetch API)**
- **Docker + Docker Compose (optional)**


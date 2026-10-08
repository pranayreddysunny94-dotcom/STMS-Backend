package com.example.stms_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.stms_backend.entity.Question;
import com.example.stms_backend.service.QuestionService;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "http://localhost:5173")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(
            QuestionService questionService) {

        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<?> addQuestion(
            @RequestBody Question question,
            @RequestParam Long assessmentId) {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            questionService.addQuestion(
                                    question,
                                    assessmentId));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {

        return ResponseEntity.ok(
                questionService.getAllQuestions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    questionService.getQuestionById(id));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<?> getQuestionsByAssessment(
            @PathVariable Long assessmentId) {

        try {

            return ResponseEntity.ok(
                    questionService
                            .getQuestionsByAssessment(
                                    assessmentId));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(
            @PathVariable Long id,
            @RequestBody Question question,
            @RequestParam Long assessmentId) {

        try {

            return ResponseEntity.ok(
                    questionService.updateQuestion(
                            id,
                            question,
                            assessmentId));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(
            @PathVariable Long id) {

        try {

            questionService.deleteQuestion(id);

            return ResponseEntity.ok(
                    "Question deleted");

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
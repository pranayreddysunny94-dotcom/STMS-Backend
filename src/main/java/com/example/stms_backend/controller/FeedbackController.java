package com.example.stms_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.stms_backend.entity.Feedback;
import com.example.stms_backend.service.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "http://localhost:5173")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(
            FeedbackService feedbackService) {

        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<?> addFeedback(
            @RequestParam Long studentId,
            @RequestParam Long trainingProgramId,
            @RequestParam Integer rating,
            @RequestParam String feedback) {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            feedbackService.addFeedback(
                                    studentId,
                                    trainingProgramId,
                                    rating,
                                    feedback
                            )
                    );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {

        return ResponseEntity.ok(
                feedbackService.getAllFeedback()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFeedbackById(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    feedbackService.getFeedbackById(id)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getFeedbackByStudent(
            @PathVariable Long studentId) {

        try {

            return ResponseEntity.ok(
                    feedbackService
                            .getFeedbackByStudent(studentId)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/program/{trainingProgramId}")
    public ResponseEntity<?> getFeedbackByProgram(
            @PathVariable Long trainingProgramId) {

        try {

            return ResponseEntity.ok(
                    feedbackService
                            .getFeedbackByProgram(
                                    trainingProgramId
                            )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}/program/{trainingProgramId}")
    public ResponseEntity<?> getFeedbackByStudentAndProgram(
            @PathVariable Long studentId,
            @PathVariable Long trainingProgramId) {

        try {

            return ResponseEntity.ok(
                    feedbackService
                            .getFeedbackByStudentAndProgram(
                                    studentId,
                                    trainingProgramId
                            )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(
            @PathVariable Long id) {

        try {

            feedbackService.deleteFeedback(id);

            return ResponseEntity.ok(
                    "Feedback deleted"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
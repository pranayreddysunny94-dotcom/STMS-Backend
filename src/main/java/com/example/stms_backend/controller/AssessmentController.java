package com.example.stms_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.stms_backend.entity.Assessment;
import com.example.stms_backend.service.AssessmentService;

@RestController
@RequestMapping("/api/assessments")
@CrossOrigin(origins = "http://localhost:5173")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(
            AssessmentService assessmentService) {

        this.assessmentService = assessmentService;
    }

    @PostMapping
    public ResponseEntity<?> addAssessment(
            @RequestBody Assessment assessment,
            @RequestParam Long trainingProgramId) {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            assessmentService.addAssessment(
                                    assessment,
                                    trainingProgramId));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Assessment>> getAllAssessments() {

        return ResponseEntity.ok(
                assessmentService.getAllAssessments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssessmentById(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    assessmentService.getAssessmentById(id));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/program/{trainingProgramId}")
    public ResponseEntity<?> getAssessmentsByProgram(
            @PathVariable Long trainingProgramId) {

        try {

            return ResponseEntity.ok(
                    assessmentService
                            .getAssessmentsByProgram(
                                    trainingProgramId));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssessment(
            @PathVariable Long id,
            @RequestBody Assessment assessment,
            @RequestParam Long trainingProgramId) {

        try {

            return ResponseEntity.ok(
                    assessmentService.updateAssessment(
                            id,
                            assessment,
                            trainingProgramId));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssessment(
            @PathVariable Long id) {

        try {

            assessmentService.deleteAssessment(id);

            return ResponseEntity.ok(
                    "Assessment deleted");

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
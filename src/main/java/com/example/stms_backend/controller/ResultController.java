package com.example.stms_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.stms_backend.entity.Result;
import com.example.stms_backend.service.ResultService;
import com.example.stms_backend.dto.AssessmentSubmissionRequest;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "http://localhost:5173")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping
    public ResponseEntity<?> addResult(
            @RequestBody Result result) {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(resultService.addResult(result));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Result>> getAllResults() {

        return ResponseEntity.ok(
                resultService.getAllResults());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResultById(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    resultService.getResultById(id));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Result>> getResultsByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                resultService.getResultsByStudent(studentId));
    }

    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<List<Result>> getResultsByAssessment(
            @PathVariable Long assessmentId) {

        return ResponseEntity.ok(
                resultService.getResultsByAssessment(assessmentId));
    }

    @GetMapping("/student/{studentId}/assessment/{assessmentId}")
    public ResponseEntity<?> getStudentAssessmentResult(
            @PathVariable Long studentId,
            @PathVariable Long assessmentId) {

        try {

            return ResponseEntity.ok(
                    resultService.getStudentAssessmentResult(
                            studentId,
                            assessmentId));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResult(
            @PathVariable Long id) {

        try {

            resultService.deleteResult(id);

            return ResponseEntity.ok(
                    "Result deleted");

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @PostMapping("/submit")
public ResponseEntity<?> submitAssessment(
        @RequestBody AssessmentSubmissionRequest request) {

    try {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        resultService.submitAssessment(request));

    } catch (RuntimeException e) {

        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
}
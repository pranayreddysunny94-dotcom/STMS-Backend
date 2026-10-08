package com.example.stms_backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.stms_backend.entity.AssignmentSubmission;
import com.example.stms_backend.service.AssignmentSubmissionService;

@RestController
@RequestMapping("/api/assignment-submissions")
@CrossOrigin(origins = "http://localhost:5173")
public class AssignmentSubmissionController {

    private final AssignmentSubmissionService submissionService;

    public AssignmentSubmissionController(
            AssignmentSubmissionService submissionService) {

        this.submissionService = submissionService;
    }

    @PostMapping
    public ResponseEntity<?> submitAssignment(
            @RequestParam Long assignmentId,
            @RequestParam Long studentId,
            @RequestParam("file") MultipartFile file) {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            submissionService.submitAssignment(
                                    assignmentId,
                                    studentId,
                                    file
                            )
                    );

        } catch (IOException e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to save assignment file.");

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<?> getSubmissionsByAssignment(
            @PathVariable Long assignmentId) {

        try {

            List<AssignmentSubmission> submissions =
                    submissionService
                            .getSubmissionsByAssignment(
                                    assignmentId
                            );

            return ResponseEntity.ok(submissions);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getSubmissionsByStudent(
            @PathVariable Long studentId) {

        try {

            List<AssignmentSubmission> submissions =
                    submissionService
                            .getSubmissionsByStudent(
                                    studentId
                            );

            return ResponseEntity.ok(submissions);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubmissionById(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    submissionService
                            .getSubmissionById(id)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
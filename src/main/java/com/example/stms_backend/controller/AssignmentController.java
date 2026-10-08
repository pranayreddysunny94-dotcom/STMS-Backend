package com.example.stms_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.stms_backend.entity.Assignment;
import com.example.stms_backend.service.AssignmentService;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "http://localhost:5173")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(
            AssignmentService assignmentService) {

        this.assignmentService =
                assignmentService;
    }

    @PostMapping
    public ResponseEntity<?> addAssignment(
            @RequestBody Assignment assignment,
            @RequestParam Long trainingProgramId) {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            assignmentService.addAssignment(
                                    assignment,
                                    trainingProgramId
                            )
                    );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Assignment>>
            getAllAssignments() {

        return ResponseEntity.ok(
                assignmentService
                        .getAllAssignments()
        );
    }

    @GetMapping("/program/{trainingProgramId}")
    public ResponseEntity<?> getAssignmentsByProgram(
            @PathVariable Long trainingProgramId) {

        try {

            return ResponseEntity.ok(
                    assignmentService
                            .getAssignmentsByProgram(
                                    trainingProgramId
                            )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignmentById(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    assignmentService
                            .getAssignmentById(id)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssignment(
            @PathVariable Long id,
            @RequestBody Assignment assignment,
            @RequestParam Long trainingProgramId) {

        try {

            return ResponseEntity.ok(
                    assignmentService.updateAssignment(
                            id,
                            assignment,
                            trainingProgramId
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssignment(
            @PathVariable Long id) {

        try {

            assignmentService.deleteAssignment(id);

            return ResponseEntity.ok(
                    "Assignment deleted"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
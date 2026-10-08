package com.example.stms_backend.controller;

import com.example.stms_backend.entity.Enrollment;
import com.example.stms_backend.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "http://localhost:5173")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(
            EnrollmentService enrollmentService) {

        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<?> addEnrollment(
            @RequestParam Long studentId,
            @RequestParam Long trainingProgramId) {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            enrollmentService.addEnrollment(
                                    studentId,
                                    trainingProgramId
                            )
                    );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> addEnrollmentByUser(
            @PathVariable Long userId,
            @RequestParam Long trainingProgramId) {

        try {

            var student =
                    enrollmentService.getStudentByUserId(userId);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            enrollmentService.addEnrollment(
                                    student.getId(),
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
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {

        return ResponseEntity.ok(
                enrollmentService.getAllEnrollments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnrollmentById(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    enrollmentService.getEnrollmentById(id)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getEnrollmentsByStudent(
            @PathVariable Long studentId) {

        try {

            return ResponseEntity.ok(
                    enrollmentService
                            .getEnrollmentsByStudent(studentId)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getEnrollmentsByUser(
            @PathVariable Long userId) {

        try {

            var student =
                    enrollmentService.getStudentByUserId(userId);

            return ResponseEntity.ok(
                    enrollmentService
                            .getEnrollmentsByStudent(
                                    student.getId()
                            )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/program/{trainingProgramId}")
    public ResponseEntity<?> getEnrollmentsByProgram(
            @PathVariable Long trainingProgramId) {

        try {

            return ResponseEntity.ok(
                    enrollmentService
                            .getEnrollmentsByProgram(
                                    trainingProgramId
                            )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/trainer/{trainer}/pending")
    public ResponseEntity<?> getPendingEnrollmentsByTrainer(
        @PathVariable String trainer) {
                try {
                        return ResponseEntity.ok(
                                enrollmentService
                                .getPendingEnrollmentsByTrainer(trainer)
                        );
                } catch (RuntimeException e) {
                        return ResponseEntity
                        .badRequest()
                        .body(e.getMessage());
                }
        }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnrollment(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        try {

            String status = request.get("status");

            if (status == null || status.isBlank()) {

                return ResponseEntity
                        .badRequest()
                        .body("Status is required");
            }

            return ResponseEntity.ok(
                    enrollmentService.updateEnrollment(
                            id,
                            status
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnrollment(
            @PathVariable Long id) {

        try {

            enrollmentService.deleteEnrollment(id);

            return ResponseEntity.ok(
                    "Enrollment deleted"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
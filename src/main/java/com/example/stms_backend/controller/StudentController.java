package com.example.stms_backend.controller;

import com.example.stms_backend.entity.Student;
import com.example.stms_backend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<?> addStudent(
            @RequestBody Student student) {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(studentService.addStudent(student));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {

        return ResponseEntity.ok(
                studentService.getAllStudents());
    }

    // IMPORTANT:
    // Keep this BEFORE /{id}
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getStudentByUserId(
            @PathVariable Long userId) {

        try {

            return ResponseEntity.ok(
                    studentService.getStudentByUserId(userId));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    studentService.getStudentById(id));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {

        try {

            return ResponseEntity.ok(
                    studentService.updateStudent(
                            id,
                            student));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(
            @PathVariable Long id) {

        try {

            studentService.deleteStudent(id);

            return ResponseEntity.ok(
                    "Student deleted");

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
package com.example.stms_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stms_backend.entity.TrainingProgram;
import com.example.stms_backend.service.TrainingProgramService;

@RestController
@RequestMapping("/api/training-programs")
@CrossOrigin(origins = "http://localhost:5173")
public class TrainingProgramController {

    private final TrainingProgramService trainingProgramService;

    public TrainingProgramController(
            TrainingProgramService trainingProgramService) {
        this.trainingProgramService = trainingProgramService;
    }

    @PostMapping
    public ResponseEntity<?> addProgram(
            @RequestBody TrainingProgram program) {

        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(trainingProgramService.addProgram(program));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TrainingProgram>> getAllPrograms() {
        return ResponseEntity.ok(
                trainingProgramService.getAllPrograms()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProgramById(
            @PathVariable Long id) {

        try {
            return ResponseEntity.ok(
                    trainingProgramService.getProgramById(id)
            );

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProgram(
            @PathVariable Long id,
            @RequestBody TrainingProgram program) {

        try {
            return ResponseEntity.ok(
                    trainingProgramService.updateProgram(id, program)
            );

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProgram(
            @PathVariable Long id) {

        try {
            trainingProgramService.deleteProgram(id);

            return ResponseEntity.ok("Training program deleted");

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
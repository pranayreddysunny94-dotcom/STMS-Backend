package com.example.stms_backend.controller;

import com.example.stms_backend.entity.Trainer;
import com.example.stms_backend.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@CrossOrigin(origins = "http://localhost:5173")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<?> addTrainer(@RequestBody Trainer trainer) {

        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(trainerService.addTrainer(trainer));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Trainer>> getAllTrainers() {

        return ResponseEntity.ok(
                trainerService.getAllTrainers()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainerById(
            @PathVariable Long id) {

        try {
            return ResponseEntity.ok(
                    trainerService.getTrainerById(id)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrainer(
            @PathVariable Long id,
            @RequestBody Trainer trainer) {

        try {
            return ResponseEntity.ok(
                    trainerService.updateTrainer(id, trainer)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrainer(
            @PathVariable Long id) {

        try {
            trainerService.deleteTrainer(id);

            return ResponseEntity.ok("Trainer deleted");

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
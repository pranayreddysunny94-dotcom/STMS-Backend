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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.stms_backend.entity.TrainingModule;
import com.example.stms_backend.service.TrainingModuleService;

@RestController
@RequestMapping("/api/training-modules")
@CrossOrigin(origins = "http://localhost:5173")
public class TrainingModuleController {

    private final TrainingModuleService trainingModuleService;

    public TrainingModuleController(
            TrainingModuleService trainingModuleService) {

        this.trainingModuleService = trainingModuleService;
    }

    @PostMapping
    public ResponseEntity<?> addModule(
            @RequestParam Long trainingProgramId,
            @RequestBody TrainingModule module) {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            trainingModuleService.addModule(
                                    module,
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
    public ResponseEntity<List<TrainingModule>> getAllModules() {

        return ResponseEntity.ok(
                trainingModuleService.getAllModules()
        );
    }

    @GetMapping("/program/{trainingProgramId}")
    public ResponseEntity<List<TrainingModule>> getModulesByProgram(
            @PathVariable Long trainingProgramId) {

        return ResponseEntity.ok(
                trainingModuleService.getModulesByProgram(
                        trainingProgramId
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getModuleById(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    trainingModuleService.getModuleById(id)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateModule(
            @PathVariable Long id,
            @RequestParam Long trainingProgramId,
            @RequestBody TrainingModule module) {

        try {

            return ResponseEntity.ok(
                    trainingModuleService.updateModule(
                            id,
                            module,
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
    public ResponseEntity<?> deleteModule(
            @PathVariable Long id) {

        try {

            trainingModuleService.deleteModule(id);

            return ResponseEntity.ok(
                    "Training module deleted"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
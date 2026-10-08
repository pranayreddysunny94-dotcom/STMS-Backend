package com.example.stms_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.stms_backend.entity.Project;
import com.example.stms_backend.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:5173")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // ==========================================
    // CREATE PROJECT
    // ==========================================

    @PostMapping
    public ResponseEntity<?> createProject(
            @RequestBody Project project) {

        try {
            Project savedProject =
                    projectService.createProject(project);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedProject);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // ==========================================
    // GET ALL PROJECTS
    // ==========================================

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {

        return ResponseEntity.ok(
                projectService.getAllProjects()
        );
    }

    // ==========================================
    // GET PROJECT BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(
            @PathVariable Long id) {

        try {

            Project project =
                    projectService.getProjectById(id);

            return ResponseEntity.ok(project);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ==========================================
    // GET PROJECTS BY STUDENT
    // ==========================================

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Project>> getProjectsByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                projectService.getProjectsByStudent(studentId)
        );
    }

    // ==========================================
    // UPDATE PROJECT
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(
            @PathVariable Long id,
            @RequestBody Project project) {

        try {

            Project updatedProject =
                    projectService.updateProject(id, project);

            return ResponseEntity.ok(updatedProject);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ==========================================
    // DELETE PROJECT
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(
            @PathVariable Long id) {

        try {

            projectService.deleteProject(id);

            return ResponseEntity.ok(
                    "Project deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ==========================================
    // SUBMIT PROJECT FILE
    // ==========================================

    @PostMapping(
            value = "/{id}/submit",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<?> submitProject(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        try {

            // Check file
            if (file == null || file.isEmpty()) {

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Please select a project file.");
            }

            // Submit file through service
            Project submittedProject =
                    projectService.submitProject(id, file);

            return ResponseEntity.ok(submittedProject);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to submit project file.");
        }
    }
}
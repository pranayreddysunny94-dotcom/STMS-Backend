package com.example.stms_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.stms_backend.entity.Assignment;
import com.example.stms_backend.entity.TrainingProgram;
import com.example.stms_backend.repository.AssignmentRepository;
import com.example.stms_backend.repository.TrainingProgramRepository;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final TrainingProgramRepository trainingProgramRepository;

    public AssignmentService(
            AssignmentRepository assignmentRepository,
            TrainingProgramRepository trainingProgramRepository) {

        this.assignmentRepository = assignmentRepository;
        this.trainingProgramRepository =
                trainingProgramRepository;
    }

    public Assignment addAssignment(
            Assignment assignment,
            Long trainingProgramId) {

        TrainingProgram trainingProgram =
                trainingProgramRepository
                        .findById(trainingProgramId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Training program not found"));

        assignment.setTrainingProgram(trainingProgram);

        if (assignment.getStatus() == null ||
                assignment.getStatus().isBlank()) {

            assignment.setStatus("ACTIVE");

        } else {

            assignment.setStatus(
                    assignment.getStatus().toUpperCase());
        }

        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAllAssignments() {

        return assignmentRepository.findAll();
    }

    public List<Assignment> getAssignmentsByProgram(
            Long trainingProgramId) {

        if (!trainingProgramRepository
                .existsById(trainingProgramId)) {

            throw new RuntimeException(
                    "Training program not found");
        }

        return assignmentRepository
                .findByTrainingProgramId(
                        trainingProgramId);
    }

    public Assignment getAssignmentById(Long id) {

        return assignmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assignment not found"));
    }

    public Assignment updateAssignment(
            Long id,
            Assignment updatedAssignment,
            Long trainingProgramId) {

        Assignment assignment =
                getAssignmentById(id);

        TrainingProgram trainingProgram =
                trainingProgramRepository
                        .findById(trainingProgramId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Training program not found"));

        assignment.setTitle(
                updatedAssignment.getTitle());

        assignment.setDescription(
                updatedAssignment.getDescription());

        assignment.setDueDate(
                updatedAssignment.getDueDate());

        assignment.setStatus(
                updatedAssignment.getStatus());

        assignment.setTrainingProgram(
                trainingProgram);

        return assignmentRepository.save(
                assignment);
    }

    public void deleteAssignment(Long id) {

        Assignment assignment =
                getAssignmentById(id);

        assignmentRepository.delete(assignment);
    }
}
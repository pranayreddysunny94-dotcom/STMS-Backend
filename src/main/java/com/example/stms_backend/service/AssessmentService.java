package com.example.stms_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.stms_backend.entity.Assessment;
import com.example.stms_backend.entity.TrainingProgram;
import com.example.stms_backend.repository.AssessmentRepository;
import com.example.stms_backend.repository.TrainingProgramRepository;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final TrainingProgramRepository trainingProgramRepository;

    public AssessmentService(
            AssessmentRepository assessmentRepository,
            TrainingProgramRepository trainingProgramRepository) {

        this.assessmentRepository = assessmentRepository;
        this.trainingProgramRepository = trainingProgramRepository;
    }

    // ==========================================
    // CREATE ASSESSMENT
    // ==========================================

    public Assessment addAssessment(
            Assessment assessment,
            Long trainingProgramId) {

        TrainingProgram trainingProgram =
                trainingProgramRepository.findById(trainingProgramId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Training program not found"));

        assessment.setTrainingProgram(trainingProgram);

        return assessmentRepository.save(assessment);
    }

    // ==========================================
    // GET ALL ASSESSMENTS
    // ==========================================

    public List<Assessment> getAllAssessments() {

        return assessmentRepository.findAll();
    }

    // ==========================================
    // GET ASSESSMENT BY ID
    // ==========================================

    public Assessment getAssessmentById(Long id) {

        return assessmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assessment not found"));
    }

    // ==========================================
    // GET ASSESSMENTS BY TRAINING PROGRAM
    // ==========================================

    public List<Assessment> getAssessmentsByProgram(
            Long trainingProgramId) {

        return assessmentRepository
                .findByTrainingProgramId(trainingProgramId);
    }

    // ==========================================
    // UPDATE ASSESSMENT
    // ==========================================

    public Assessment updateAssessment(
            Long id,
            Assessment updatedAssessment,
            Long trainingProgramId) {

        Assessment existingAssessment =
                getAssessmentById(id);

        existingAssessment.setTitle(
                updatedAssessment.getTitle());

        existingAssessment.setDescription(
                updatedAssessment.getDescription());

        existingAssessment.setTotalMarks(
                updatedAssessment.getTotalMarks());

        existingAssessment.setDuration(
                updatedAssessment.getDuration());

        existingAssessment.setStatus(
                updatedAssessment.getStatus());

        if (trainingProgramId != null) {

            TrainingProgram trainingProgram =
                    trainingProgramRepository.findById(
                            trainingProgramId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Training program not found"));

            existingAssessment.setTrainingProgram(
                    trainingProgram);
        }

        return assessmentRepository.save(
                existingAssessment);
    }

    // ==========================================
    // DELETE ASSESSMENT
    // ==========================================

    public void deleteAssessment(Long id) {

        Assessment assessment =
                getAssessmentById(id);

        assessmentRepository.delete(assessment);
    }
}
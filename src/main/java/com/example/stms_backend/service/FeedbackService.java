package com.example.stms_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.stms_backend.entity.Feedback;
import com.example.stms_backend.entity.Student;
import com.example.stms_backend.entity.TrainingProgram;
import com.example.stms_backend.repository.FeedbackRepository;
import com.example.stms_backend.repository.StudentRepository;
import com.example.stms_backend.repository.TrainingProgramRepository;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final StudentRepository studentRepository;
    private final TrainingProgramRepository trainingProgramRepository;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            StudentRepository studentRepository,
            TrainingProgramRepository trainingProgramRepository) {

        this.feedbackRepository = feedbackRepository;
        this.studentRepository = studentRepository;
        this.trainingProgramRepository = trainingProgramRepository;
    }

    public Feedback addFeedback(
            Long studentId,
            Long trainingProgramId,
            Integer rating,
            String feedbackText) {

        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found"));

        TrainingProgram trainingProgram =
                trainingProgramRepository
                        .findById(trainingProgramId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Training program not found"));

        if (rating == null || rating < 1 || rating > 5) {
            throw new RuntimeException(
                    "Rating must be between 1 and 5");
        }

        if (feedbackText == null ||
                feedbackText.trim().isEmpty()) {

            throw new RuntimeException(
                    "Feedback cannot be empty");
        }

        Feedback feedback = new Feedback();

        feedback.setStudent(student);
        feedback.setTrainingProgram(trainingProgram);
        feedback.setRating(rating);
        feedback.setFeedback(feedbackText.trim());

        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedback() {

        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(Long id) {

        return feedbackRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Feedback not found"));
    }

    public List<Feedback> getFeedbackByStudent(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found");
        }

        return feedbackRepository
                .findByStudentId(studentId);
    }

    public List<Feedback> getFeedbackByProgram(
            Long trainingProgramId) {

        if (!trainingProgramRepository
                .existsById(trainingProgramId)) {

            throw new RuntimeException(
                    "Training program not found");
        }

        return feedbackRepository
                .findByTrainingProgramId(
                        trainingProgramId);
    }

    public List<Feedback> getFeedbackByStudentAndProgram(
            Long studentId,
            Long trainingProgramId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found");
        }

        if (!trainingProgramRepository
                .existsById(trainingProgramId)) {

            throw new RuntimeException(
                    "Training program not found");
        }

        return feedbackRepository
                .findByStudentIdAndTrainingProgramId(
                        studentId,
                        trainingProgramId);
    }

    public void deleteFeedback(Long id) {

        Feedback feedback = getFeedbackById(id);

        feedbackRepository.delete(feedback);
    }
}
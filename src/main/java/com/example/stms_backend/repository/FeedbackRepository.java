package com.example.stms_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stms_backend.entity.Feedback;

public interface FeedbackRepository
        extends JpaRepository<Feedback, Long> {

    List<Feedback> findByStudentId(Long studentId);

    List<Feedback> findByTrainingProgramId(Long trainingProgramId);

    List<Feedback> findByStudentIdAndTrainingProgramId(
            Long studentId,
            Long trainingProgramId
    );
}
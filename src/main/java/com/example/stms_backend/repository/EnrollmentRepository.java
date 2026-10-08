package com.example.stms_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stms_backend.entity.Enrollment;

public interface EnrollmentRepository
        extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByTrainingProgramId(Long trainingProgramId);

    boolean existsByStudentIdAndTrainingProgramId(
            Long studentId,
            Long trainingProgramId
    );

    List<Enrollment> findByTrainingProgramTrainerAndStatus(
            String trainer,
            String status
    );
}
package com.example.stms_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stms_backend.entity.Assignment;

public interface AssignmentRepository
        extends JpaRepository<Assignment, Long> {

    List<Assignment> findByTrainingProgramId(
            Long trainingProgramId
    );
}
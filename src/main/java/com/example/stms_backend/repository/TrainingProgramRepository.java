package com.example.stms_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stms_backend.entity.TrainingProgram;

public interface TrainingProgramRepository
        extends JpaRepository<TrainingProgram, Long> {
}
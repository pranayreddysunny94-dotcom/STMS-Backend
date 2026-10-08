package com.example.stms_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stms_backend.entity.TrainingModule;

public interface TrainingModuleRepository
        extends JpaRepository<TrainingModule, Long> {

    List<TrainingModule> findByTrainingProgramId(Long trainingProgramId);
}
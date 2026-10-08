package com.example.stms_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stms_backend.entity.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findByTrainerId(String trainerId);

    boolean existsByTrainerId(String trainerId);
}
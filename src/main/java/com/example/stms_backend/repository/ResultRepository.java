package com.example.stms_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stms_backend.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByStudentId(Long studentId);

    List<Result> findByAssessmentId(Long assessmentId);

    List<Result> findByStudentIdAndAssessmentId(
            Long studentId,
            Long assessmentId);
}
package com.example.stms_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stms_backend.entity.Attendance;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByTrainingProgramId(Long trainingProgramId);

    List<Attendance> findByStudentIdAndTrainingProgramId(
            Long studentId,
            Long trainingProgramId
    );

    boolean existsByStudentIdAndTrainingProgramIdAndAttendanceDate(
            Long studentId,
            Long trainingProgramId,
            java.time.LocalDate attendanceDate
    );
    long countByTrainingProgramIdAndAttendanceDate(
        Long trainingProgramId,
        java.time.LocalDate attendanceDate
);
}
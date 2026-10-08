package com.example.stms_backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.stms_backend.entity.Enrollment;
import com.example.stms_backend.entity.Student;
import com.example.stms_backend.entity.TrainingProgram;
import com.example.stms_backend.repository.EnrollmentRepository;
import com.example.stms_backend.repository.StudentRepository;
import com.example.stms_backend.repository.TrainingProgramRepository;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final TrainingProgramRepository trainingProgramRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            TrainingProgramRepository trainingProgramRepository) {

        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.trainingProgramRepository = trainingProgramRepository;
    }

    public Enrollment addEnrollment(
            Long studentId,
            Long trainingProgramId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        TrainingProgram trainingProgram =
                trainingProgramRepository.findById(trainingProgramId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Training program not found"));

        if (enrollmentRepository
                .existsByStudentIdAndTrainingProgramId(
                        studentId,
                        trainingProgramId)) {

            throw new RuntimeException(
                    "Student is already enrolled in this training program");
        }

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setTrainingProgram(trainingProgram);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("PENDING");

        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(Long id) {

        return enrollmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Enrollment not found"));
    }

    public List<Enrollment> getEnrollmentsByStudent(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found");
        }

        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> getEnrollmentsByProgram(
            Long trainingProgramId) {

        if (!trainingProgramRepository.existsById(trainingProgramId)) {
            throw new RuntimeException(
                    "Training program not found");
        }

        return enrollmentRepository
                .findByTrainingProgramId(trainingProgramId);
    }

    public Enrollment updateEnrollment(
            Long id,
            String status) {

        Enrollment enrollment = getEnrollmentById(id);

        enrollment.setStatus(status.toUpperCase());

        return enrollmentRepository.save(enrollment);
    }
    public List<Enrollment> getPendingEnrollmentsByTrainer(
        String trainer) {

    return enrollmentRepository
            .findByTrainingProgramTrainerAndStatus(
                    trainer,
                    "PENDING"
            );
        }

    public void deleteEnrollment(Long id) {

        Enrollment enrollment = getEnrollmentById(id);

        enrollmentRepository.delete(enrollment);
    }

    public Student getStudentByUserId(Long userId) {
        return studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));
    }
}

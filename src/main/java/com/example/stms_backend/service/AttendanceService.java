package com.example.stms_backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.stms_backend.entity.Attendance;
import com.example.stms_backend.entity.Student;
import com.example.stms_backend.entity.TrainingProgram;
import com.example.stms_backend.repository.AttendanceRepository;
import com.example.stms_backend.repository.StudentRepository;
import com.example.stms_backend.repository.TrainingProgramRepository;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final TrainingProgramRepository trainingProgramRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            StudentRepository studentRepository,
            TrainingProgramRepository trainingProgramRepository) {

        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.trainingProgramRepository = trainingProgramRepository;
    }

    public Attendance addAttendance(
            Long studentId,
            Long trainingProgramId,
            LocalDate attendanceDate,
            String status) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        TrainingProgram trainingProgram =
                trainingProgramRepository.findById(trainingProgramId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Training program not found"));

        if (attendanceRepository
                .existsByStudentIdAndTrainingProgramIdAndAttendanceDate(
                        studentId,
                        trainingProgramId,
                        attendanceDate)) {

            throw new RuntimeException(
                    "Attendance already marked for this date");
        }

        Attendance attendance = new Attendance();

        attendance.setStudent(student);
        attendance.setTrainingProgram(trainingProgram);
        attendance.setAttendanceDate(attendanceDate);
        attendance.setStatus(status.toUpperCase());

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Attendance not found"));
    }

    public List<Attendance> getAttendanceByStudent(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found");
        }

        return attendanceRepository.findByStudentId(studentId);
    }

    public List<Attendance> getAttendanceByProgram(
            Long trainingProgramId) {

        if (!trainingProgramRepository.existsById(trainingProgramId)) {
            throw new RuntimeException(
                    "Training program not found");
        }

        return attendanceRepository
                .findByTrainingProgramId(trainingProgramId);
    }

    public List<Attendance> getAttendanceByStudentAndProgram(
            Long studentId,
            Long trainingProgramId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found");
        }

        if (!trainingProgramRepository.existsById(trainingProgramId)) {
            throw new RuntimeException(
                    "Training program not found");
        }

        return attendanceRepository
                .findByStudentIdAndTrainingProgramId(
                        studentId,
                        trainingProgramId);
    }

    public Attendance updateAttendance(
            Long id,
            String status) {

        Attendance attendance = getAttendanceById(id);

        attendance.setStatus(status.toUpperCase());

        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(Long id) {

        Attendance attendance = getAttendanceById(id);

        attendanceRepository.delete(attendance);
    }
    public List<Attendance> getAttendanceByUser(Long userId) {

    Student student = studentRepository.findByUserId(userId)
            .orElseThrow(() ->
                    new RuntimeException("Student profile not found"));

    return attendanceRepository.findByStudentId(student.getId());
}
public boolean isAttendanceSubmittedToday(
        Long trainingProgramId) {

    LocalDate today = LocalDate.now();

    long attendanceCount =
            attendanceRepository
                    .countByTrainingProgramIdAndAttendanceDate(
                            trainingProgramId,
                            today
                    );

    long studentCount = studentRepository.count();

    return attendanceCount >= studentCount;
}
}
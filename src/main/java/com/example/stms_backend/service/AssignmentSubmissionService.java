package com.example.stms_backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.stms_backend.entity.Assignment;
import com.example.stms_backend.entity.AssignmentSubmission;
import com.example.stms_backend.entity.Student;
import com.example.stms_backend.repository.AssignmentRepository;
import com.example.stms_backend.repository.AssignmentSubmissionRepository;
import com.example.stms_backend.repository.StudentRepository;

@Service
public class AssignmentSubmissionService {

    private final AssignmentSubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;

    private final String uploadDirectory = "uploads/assignments";

    public AssignmentSubmissionService(
            AssignmentSubmissionRepository submissionRepository,
            AssignmentRepository assignmentRepository,
            StudentRepository studentRepository) {

        this.submissionRepository = submissionRepository;
        this.assignmentRepository = assignmentRepository;
        this.studentRepository = studentRepository;
    }

    public AssignmentSubmission submitAssignment(
            Long assignmentId,
            Long studentId,
            MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException(
                    "Please select a file");
        }

        Assignment assignment =
                assignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Assignment not found"));

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student not found"));

        AssignmentSubmission submission =
                submissionRepository
                        .findByAssignmentIdAndStudentId(
                                assignmentId,
                                studentId)
                        .orElse(new AssignmentSubmission());

        Path uploadPath =
                Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName =
                file.getOriginalFilename();

        if (originalFileName == null ||
                originalFileName.isBlank()) {

            throw new RuntimeException(
                    "Invalid file name");
        }

        String fileName =
                System.currentTimeMillis()
                        + "_"
                        + originalFileName;

        Path filePath =
                uploadPath.resolve(fileName);

        Files.write(
                filePath,
                file.getBytes());

        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setFileName(originalFileName);
        submission.setFilePath(filePath.toString());
        submission.setSubmittedAt(LocalDateTime.now());
        submission.setStatus("SUBMITTED");

        return submissionRepository.save(
                submission);
    }

    public List<AssignmentSubmission>
            getSubmissionsByAssignment(
                    Long assignmentId) {

        if (!assignmentRepository
                .existsById(assignmentId)) {

            throw new RuntimeException(
                    "Assignment not found");
        }

        return submissionRepository
                .findByAssignmentId(assignmentId);
    }

    public List<AssignmentSubmission>
            getSubmissionsByStudent(
                    Long studentId) {

        if (!studentRepository
                .existsById(studentId)) {

            throw new RuntimeException(
                    "Student not found");
        }

        return submissionRepository
                .findByStudentId(studentId);
    }

    public AssignmentSubmission
            getSubmissionById(Long id) {

        return submissionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Submission not found"));
    }
}
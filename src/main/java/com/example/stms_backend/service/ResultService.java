package com.example.stms_backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.stms_backend.dto.AssessmentSubmissionRequest;
import com.example.stms_backend.entity.Assessment;
import com.example.stms_backend.entity.Question;
import com.example.stms_backend.entity.Result;
import com.example.stms_backend.entity.Student;
import com.example.stms_backend.repository.AssessmentRepository;
import com.example.stms_backend.repository.QuestionRepository;
import com.example.stms_backend.repository.ResultRepository;
import com.example.stms_backend.repository.StudentRepository;

@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private final AssessmentRepository assessmentRepository;
    private final QuestionRepository questionRepository;

    public ResultService(
            ResultRepository resultRepository,
            StudentRepository studentRepository,
            AssessmentRepository assessmentRepository,
            QuestionRepository questionRepository) {

        this.resultRepository = resultRepository;
        this.studentRepository = studentRepository;
        this.assessmentRepository = assessmentRepository;
        this.questionRepository = questionRepository;
    }

    public Result submitAssessment(
            AssessmentSubmissionRequest request) {

        Student student = studentRepository
                .findById(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found"));

        Assessment assessment = assessmentRepository
                .findById(request.getAssessmentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assessment not found"));

        List<Question> questions =
                questionRepository
                        .findByAssessmentId(
                                request.getAssessmentId());

        Map<Long, String> answers = request.getAnswers();

        int marksObtained = 0;

        for (Question question : questions) {

            String selectedAnswer =
                    answers != null
                            ? answers.get(question.getId())
                            : null;

            if (selectedAnswer != null &&
                    selectedAnswer.equalsIgnoreCase(
                            question.getCorrectAnswer())) {

                marksObtained += question.getMarks();
            }
        }

        int totalMarks = assessment.getTotalMarks();

        double percentage = 0.0;

        if (totalMarks > 0) {
            percentage =
                    ((double) marksObtained / totalMarks) * 100;
        }

        String grade;

        if (percentage >= 90) {
            grade = "A+";
        } else if (percentage >= 80) {
            grade = "A";
        } else if (percentage >= 70) {
            grade = "B";
        } else if (percentage >= 60) {
            grade = "C";
        } else if (percentage >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }

        String resultStatus =
                percentage >= 40 ? "PASS" : "FAIL";

        Result result = new Result();

        result.setStudent(student);
        result.setAssessment(assessment);
        result.setMarksObtained(marksObtained);
        result.setPercentage(percentage);
        result.setGrade(grade);
        result.setResultStatus(resultStatus);

        return resultRepository.save(result);
    }

    public Result addResult(Result result) {
        return resultRepository.save(result);
    }

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Result getResultById(Long id) {

        return resultRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Result not found"));
    }

    public List<Result> getResultsByStudent(Long studentId) {

        return resultRepository.findByStudentId(studentId);
    }

    public List<Result> getResultsByAssessment(
            Long assessmentId) {

        return resultRepository.findByAssessmentId(assessmentId);
    }

    public Result getStudentAssessmentResult(
            Long studentId,
            Long assessmentId) {

        return resultRepository
                .findByStudentIdAndAssessmentId(
                        studentId,
                        assessmentId)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Result not found"));
    }

    public void deleteResult(Long id) {

        Result result = getResultById(id);

        resultRepository.delete(result);
    }
}
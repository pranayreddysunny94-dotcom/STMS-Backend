package com.example.stms_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.stms_backend.entity.Assessment;
import com.example.stms_backend.entity.Question;
import com.example.stms_backend.repository.AssessmentRepository;
import com.example.stms_backend.repository.QuestionRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AssessmentRepository assessmentRepository;

    public QuestionService(
            QuestionRepository questionRepository,
            AssessmentRepository assessmentRepository) {

        this.questionRepository = questionRepository;
        this.assessmentRepository = assessmentRepository;
    }

    /* =========================================
       ADD QUESTION
    ========================================= */

    public Question addQuestion(
            Question question,
            Long assessmentId) {

        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() ->
                        new RuntimeException("Assessment not found"));

        question.setAssessment(assessment);

        Question savedQuestion = questionRepository.save(question);

        updateAssessmentTotalMarks(assessmentId);

        return savedQuestion;
    }

    /* =========================================
       GET ALL QUESTIONS
    ========================================= */

    public List<Question> getAllQuestions() {

        return questionRepository.findAll();
    }

    /* =========================================
       GET QUESTION BY ID
    ========================================= */

    public Question getQuestionById(Long id) {

        return questionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Question not found"));
    }

    /* =========================================
       GET QUESTIONS BY ASSESSMENT
    ========================================= */

    public List<Question> getQuestionsByAssessment(
            Long assessmentId) {

        if (!assessmentRepository.existsById(assessmentId)) {
            throw new RuntimeException("Assessment not found");
        }

        return questionRepository.findByAssessmentId(assessmentId);
    }

    /* =========================================
       UPDATE QUESTION
    ========================================= */

    public Question updateQuestion(
            Long id,
            Question updatedQuestion,
            Long assessmentId) {

        Question question = getQuestionById(id);

        Assessment oldAssessment = question.getAssessment();

        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() ->
                        new RuntimeException("Assessment not found"));

        question.setAssessment(assessment);
        question.setQuestionText(
                updatedQuestion.getQuestionText());
        question.setOptionA(
                updatedQuestion.getOptionA());
        question.setOptionB(
                updatedQuestion.getOptionB());
        question.setOptionC(
                updatedQuestion.getOptionC());
        question.setOptionD(
                updatedQuestion.getOptionD());
        question.setCorrectAnswer(
                updatedQuestion.getCorrectAnswer());
        question.setMarks(
                updatedQuestion.getMarks());

        Question savedQuestion =
                questionRepository.save(question);

        /*
         * Recalculate the new assessment.
         */
        updateAssessmentTotalMarks(assessmentId);

        /*
         * If the question was moved from another
         * assessment, recalculate the old assessment too.
         */
        if (oldAssessment != null &&
                !oldAssessment.getId().equals(assessmentId)) {

            updateAssessmentTotalMarks(
                    oldAssessment.getId());
        }

        return savedQuestion;
    }

    /* =========================================
       DELETE QUESTION
    ========================================= */

    public void deleteQuestion(Long id) {

        Question question = getQuestionById(id);

        Long assessmentId =
                question.getAssessment().getId();

        questionRepository.delete(question);

        updateAssessmentTotalMarks(assessmentId);
    }

    /* =========================================
       UPDATE ASSESSMENT TOTAL MARKS
    ========================================= */

    private void updateAssessmentTotalMarks(
            Long assessmentId) {

        Assessment assessment =
                assessmentRepository.findById(assessmentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Assessment not found"));

        List<Question> questions =
                questionRepository
                        .findByAssessmentId(assessmentId);

        int totalMarks = questions.stream()
                .filter(question -> question.getMarks() != null)
                .mapToInt(Question::getMarks)
                .sum();

        assessment.setTotalMarks(totalMarks);

        assessmentRepository.save(assessment);
    }
}
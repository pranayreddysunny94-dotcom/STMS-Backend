package com.example.stms_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @Column(nullable = false)
    private Integer marksObtained;

    @Column(nullable = false)
    private Double percentage;

    @Column(nullable = false)
    private String grade;

    @Column(nullable = false)
    private String resultStatus;

    public Result() {
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public Integer getMarksObtained() {
        return marksObtained;
    }

    public Double getPercentage() {
        return percentage;
    }

    public String getGrade() {
        return grade;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public void setMarksObtained(Integer marksObtained) {
        this.marksObtained = marksObtained;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }
}
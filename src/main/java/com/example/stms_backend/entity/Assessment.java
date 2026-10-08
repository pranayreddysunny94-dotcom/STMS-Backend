package com.example.stms_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "assessments")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "training_program_id", nullable = false)
    private TrainingProgram trainingProgram;

    @Column(nullable = false)
    private Integer totalMarks;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private String status;

    public Assessment() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
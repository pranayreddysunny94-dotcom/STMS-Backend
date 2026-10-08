package com.example.stms_backend.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    private LocalDate dueDate;

    private String status;

    @ManyToOne
    @JoinColumn(name = "training_program_id", nullable = false)
    private TrainingProgram trainingProgram;

    public Assignment() {
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
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

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTrainingProgram(
            TrainingProgram trainingProgram) {

        this.trainingProgram = trainingProgram;
    }
}
package com.example.stms_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "training_modules")
public class TrainingModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String moduleTitle;

    @Column(nullable = false, length = 1000)
    private String description;

    private Integer moduleOrder;

    private String duration;

    private String status;

    @ManyToOne
    @JoinColumn(name = "training_program_id", nullable = false)
    private TrainingProgram trainingProgram;

    public TrainingModule() {
    }

    public Long getId() {
        return id;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public String getDescription() {
        return description;
    }

    public Integer getModuleOrder() {
        return moduleOrder;
    }

    public String getDuration() {
        return duration;
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

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setModuleOrder(Integer moduleOrder) {
        this.moduleOrder = moduleOrder;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }
}
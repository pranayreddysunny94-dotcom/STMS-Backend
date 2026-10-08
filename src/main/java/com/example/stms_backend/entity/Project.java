package com.example.stms_backend.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String technology;

    private LocalDate startDate;

    private LocalDate endDate;

    private String github;

    @Column(nullable = false)
    private String status = "Ongoing";

    /*
     * Uploaded project file
     *
     * @Lob stores binary data.
     * LONGBLOB allows large ZIP/RAR/PDF/DOC/DOCX files.
     *
     * @JsonIgnore prevents the file bytes from being returned
     * when the project details API is called.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "project_file", columnDefinition = "LONGBLOB")
    @JsonIgnore
    private byte[] projectFile;

    /*
     * Original uploaded file name
     */
    @Column(name = "project_file_name")
    private String projectFileName;

    /*
     * Student who owns this project
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;


    // =========================
    // Constructor
    // =========================

    public Project() {
    }


    // =========================
    // Getters
    // =========================

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTechnology() {
        return technology;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getGithub() {
        return github;
    }

    public String getStatus() {
        return status;
    }

    public byte[] getProjectFile() {
        return projectFile;
    }

    public String getProjectFileName() {
        return projectFileName;
    }

    public Student getStudent() {
        return student;
    }


    // =========================
    // Setters
    // =========================

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProjectFile(byte[] projectFile) {
        this.projectFile = projectFile;
    }

    public void setProjectFileName(String projectFileName) {
        this.projectFileName = projectFileName;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
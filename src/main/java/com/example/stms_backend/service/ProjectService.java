package com.example.stms_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.stms_backend.entity.Project;
import com.example.stms_backend.repository.ProjectRepository;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    public Project submitProject(
        Long projectId,
        MultipartFile file) {

    Project project = projectRepository
            .findById(projectId)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Project not found with ID: " + projectId
                    )
            );

    try {

        project.setProjectFile(file.getBytes());

        project.setProjectFileName(
                file.getOriginalFilename()
        );

        return projectRepository.save(project);

    } catch (IOException e) {

        throw new RuntimeException(
                "Failed to upload project file."
        );
    }
}

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {

        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));
    }

    public List<Project> getProjectsByStudent(Long studentId) {

        return projectRepository.findByStudentId(studentId);
    }

    public Project updateProject(
            Long id,
            Project updatedProject) {

        Project existingProject =
                getProjectById(id);

        existingProject.setTitle(
                updatedProject.getTitle());

        existingProject.setDescription(
                updatedProject.getDescription());

        existingProject.setTechnology(
                updatedProject.getTechnology());

        existingProject.setStartDate(
                updatedProject.getStartDate());

        existingProject.setEndDate(
                updatedProject.getEndDate());

        existingProject.setGithub(
                updatedProject.getGithub());

        existingProject.setStatus(
                updatedProject.getStatus());

        return projectRepository.save(
                existingProject);
    }

    public void deleteProject(Long id) {

        Project project =
                getProjectById(id);

        projectRepository.delete(project);
    }

    // ==============================
    // PROJECT FILE UPLOAD
    // ==============================

    public Project uploadProjectFile(
            Long id,
            MultipartFile file) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException(
                    "Please select a project file.");
        }

        Project project =
                getProjectById(id);

        project.setProjectFile(
                file.getBytes());

        project.setProjectFileName(
                file.getOriginalFilename());

        return projectRepository.save(project);
    }
}
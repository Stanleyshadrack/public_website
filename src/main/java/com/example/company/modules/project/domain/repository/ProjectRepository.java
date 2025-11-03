package com.example.company.modules.project.domain.repository;

import com.example.company.modules.project.domain.entity.ProjectModel;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    List<ProjectModel> getAllProjects();
    Optional<ProjectModel> findProjectById(Long id);
    ProjectModel saveProject(ProjectModel project);
    void deleteProject(ProjectModel project);
}

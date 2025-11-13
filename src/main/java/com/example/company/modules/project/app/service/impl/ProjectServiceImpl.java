package com.example.company.modules.project.app.service.impl;

import com.example.company.modules.project.app.dto.ProjectDTO;
import com.example.company.modules.project.app.service.ProjectService;
import com.example.company.modules.project.domain.entity.ProjectModel;
import com.example.company.modules.project.domain.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.getAllProjects()
                .stream()
                .map(p -> new ProjectDTO(
                        p.getId(),
                        Base64.getEncoder().encodeToString(p.getImage()),
                        p.getTitle(),
                        p.getDescription()
                ))
                .toList();
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        ProjectModel p = projectRepository.findProjectById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found")
                );

        return new ProjectDTO(
                p.getId(),
                Base64.getEncoder().encodeToString(p.getImage()),
                p.getTitle(),
                p.getDescription()
        );
    }

    @Override
    public ProjectDTO createProject(ProjectDTO dto) {

        // Validate required fields
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Project title is required");
        }

        if (dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Project description is required");
        }

        if (dto.getImageBase64() == null || dto.getImageBase64().isBlank()) {
            throw new IllegalArgumentException("Project image is required");
        }

        // Decode Base64 safely
        byte[] imageBytes;
        try {
            imageBytes = Base64.getDecoder().decode(dto.getImageBase64());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Base64 image format");
        }

        ProjectModel project = new ProjectModel(imageBytes, dto.getTitle(), dto.getDescription());
        ProjectModel saved = projectRepository.saveProject(project);

        return new ProjectDTO(
                saved.getId(),
                Base64.getEncoder().encodeToString(saved.getImage()),
                saved.getTitle(),
                saved.getDescription()
        );
    }

    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO dto) {

        ProjectModel existing = projectRepository.findProjectById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found")
                );

        // Title update
        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            existing.setTitle(dto.getTitle());
        }

        // Description update
        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            existing.setDescription(dto.getDescription());
        }

        // Image update
        if (dto.getImageBase64() != null && !dto.getImageBase64().isBlank()) {
            try {
                byte[] newImage = Base64.getDecoder().decode(dto.getImageBase64());
                existing.setImage(newImage);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid Base64 image format");
            }
        }

        ProjectModel updated = projectRepository.saveProject(existing);

        return new ProjectDTO(
                updated.getId(),
                Base64.getEncoder().encodeToString(updated.getImage()),
                updated.getTitle(),
                updated.getDescription()
        );
    }

    @Override
    public void deleteProject(Long id) {
        ProjectModel existing = projectRepository.findProjectById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found")
                );

        projectRepository.deleteProject(existing);
    }
}

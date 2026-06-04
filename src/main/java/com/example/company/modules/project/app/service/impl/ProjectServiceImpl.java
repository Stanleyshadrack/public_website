package com.example.company.modules.project.app.service.impl;

import com.example.company.modules.project.app.dto.ProjectDTO;
import com.example.company.modules.project.app.service.ProjectService;
import com.example.company.modules.project.domain.entity.ProjectModel;
import com.example.company.modules.project.domain.repository.ProjectRepository;
import com.example.company.exceptions.NotFoundException; // Imported your custom exception
import org.springframework.stereotype.Service;

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
                        p.getDescription(),
                        p.getClient(),
                        p.getAchievements()
                ))
                .toList();
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        ProjectModel p = projectRepository.findProjectById(id)
                .orElseThrow(() -> new NotFoundException("Project not found")); // Converted to custom exception

        return new ProjectDTO(
                p.getId(),
                Base64.getEncoder().encodeToString(p.getImage()),
                p.getTitle(),
                p.getDescription(),
                p.getClient(),
                p.getAchievements()
        );
    }

    @Override
    public ProjectDTO createProject(ProjectDTO dto) {

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Project title is required");
        }
        if (dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Project description is required");
        }
        if (dto.getClient() == null || dto.getClient().isBlank()) {
            throw new IllegalArgumentException("Client is required");
        }
        if (dto.getImageBase64() == null || dto.getImageBase64().isBlank()) {
            throw new IllegalArgumentException("Project image is required");
        }

        projectRepository.findByClientAndTitle(dto.getClient(), dto.getTitle())
                .ifPresent(existingProject -> {
                    throw new IllegalArgumentException(
                            "Project '" + dto.getTitle() +
                                    "' already exists for client '" +
                                    dto.getClient() + "'"
                    );
                });

        byte[] imageBytes;
        try {
            imageBytes = Base64.getDecoder().decode(dto.getImageBase64());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Base64 image format");
        }

        ProjectModel project = new ProjectModel();
        project.setImage(imageBytes);
        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setClient(dto.getClient());
        project.setAchievements(dto.getAchievements());

        ProjectModel saved = projectRepository.saveProject(project);

        return new ProjectDTO(
                saved.getId(),
                Base64.getEncoder().encodeToString(saved.getImage()),
                saved.getTitle(),
                saved.getDescription(),
                saved.getClient(),
                saved.getAchievements()
        );
    }

    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO dto) {

        ProjectModel existing = projectRepository.findProjectById(id)
                .orElseThrow(() -> new NotFoundException("Project not found")); // Converted to custom exception

        String updatedTitle = (dto.getTitle() != null && !dto.getTitle().isBlank())
                ? dto.getTitle() : existing.getTitle();

        String updatedClient = (dto.getClient() != null && !dto.getClient().isBlank())
                ? dto.getClient() : existing.getClient();

        projectRepository.findByClientAndTitle(updatedClient, updatedTitle)
                .ifPresent(duplicateProject -> {
                    if (!duplicateProject.getId().equals(id)) {
                        throw new IllegalArgumentException(
                                "Project '" + updatedTitle +
                                        "' already exists for client '" +
                                        updatedClient + "'"
                        );
                    }
                });

        byte[] updatedImageBytes = null;
        if (dto.getImageBase64() != null && !dto.getImageBase64().isBlank()) {
            try {
                updatedImageBytes = Base64.getDecoder().decode(dto.getImageBase64());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid Base64 image format");
            }
        }

        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            existing.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            existing.setDescription(dto.getDescription());
        }
        if (dto.getClient() != null && !dto.getClient().isBlank()) {
            existing.setClient(dto.getClient());
        }
        if (dto.getAchievements() != null && !dto.getAchievements().isBlank()) {
            existing.setAchievements(dto.getAchievements());
        }
        if (updatedImageBytes != null) {
            existing.setImage(updatedImageBytes);
        }

        ProjectModel updated = projectRepository.saveProject(existing);

        return new ProjectDTO(
                updated.getId(),
                Base64.getEncoder().encodeToString(updated.getImage()),
                updated.getTitle(),
                updated.getDescription(),
                updated.getClient(),
                updated.getAchievements()
        );
    }

    @Override
    public void deleteProject(Long id) {
        ProjectModel existing = projectRepository.findProjectById(id)
                .orElseThrow(() -> new NotFoundException("Project not found")); // Converted to custom exception

        projectRepository.deleteProject(existing);
    }
}
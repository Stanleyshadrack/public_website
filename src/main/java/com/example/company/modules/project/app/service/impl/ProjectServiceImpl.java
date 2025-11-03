package com.example.company.modules.project.app.service.impl;



import com.example.company.modules.project.app.dto.ProjectDTO;
import com.example.company.modules.project.app.service.ProjectService;
import com.example.company.modules.project.domain.entity.ProjectModel;
import com.example.company.modules.project.domain.repository.ProjectRepository;
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
                        p.getDescription()
                ))
                .toList();
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        ProjectModel p = projectRepository.findProjectById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return new ProjectDTO(
                p.getId(),
                Base64.getEncoder().encodeToString(p.getImage()),
                p.getTitle(),
                p.getDescription()
        );
    }

    @Override
    public ProjectDTO createProject(ProjectDTO dto) {
        // ✅ decode base64 image string to byte[]
        byte[] imageBytes = Base64.getDecoder().decode(dto.getImageBase64());

        ProjectModel project = new ProjectModel(imageBytes, dto.getTitle(), dto.getDescription());
        ProjectModel saved = projectRepository.saveProject(project);

        return new ProjectDTO(
                saved.getId(),
                Base64.getEncoder().encodeToString(saved.getImage()),  // ✅ return as Base64
                saved.getTitle(),
                saved.getDescription()
        );
    }

    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO dto) {
        ProjectModel existing = projectRepository.findProjectById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // ✅ Update title only if provided
        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            existing.setTitle(dto.getTitle());
        }

        // ✅ Update description only if provided
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            existing.setDescription(dto.getDescription());
        }

        // ✅ Update image only if provided
        if (dto.getImageBase64() != null && !dto.getImageBase64().isEmpty()) {
            existing.setImage(Base64.getDecoder().decode(dto.getImageBase64()));
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
                .orElseThrow(() -> new RuntimeException("Project not found"));

        projectRepository.deleteProject(existing);
    }
}

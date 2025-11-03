package com.example.company.modules.project.app.service;


import com.example.company.modules.contact.app.dto.ContactMessageDTO;
import com.example.company.modules.project.app.dto.ProjectDTO;
import java.util.List;

public interface ProjectService {
    List<ProjectDTO> getAllProjects();
    ProjectDTO getProjectById(Long id);
    ProjectDTO createProject(ProjectDTO dto);
    ProjectDTO updateProject(Long id, ProjectDTO dto);
    void deleteProject(Long id);


}

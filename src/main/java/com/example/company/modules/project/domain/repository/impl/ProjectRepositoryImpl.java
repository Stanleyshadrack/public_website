package com.example.company.modules.project.domain.repository.impl;

import com.example.company.modules.project.domain.entity.ProjectModel;
import com.example.company.modules.project.domain.repository.ProjectRepository;
import com.example.company.modules.project.infra.jpaRepository.ProjectJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectRepositoryImpl implements ProjectRepository {

    private final ProjectJpaRepository jpaRepo;

    public ProjectRepositoryImpl(ProjectJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public List<ProjectModel> getAllProjects() {
        return jpaRepo.findAll();
    }

    @Override
    public Optional<ProjectModel> findProjectById(Long id) {
        return jpaRepo.findById(id);
    }

    @Override
    public ProjectModel saveProject(ProjectModel project) {
        return jpaRepo.save(project);
    }

    @Override
    public void deleteProject(ProjectModel project) {
        jpaRepo.delete(project);
    }
}


package com.example.company.modules.project.infra.jpaRepository;


import com.example.company.modules.project.domain.entity.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectJpaRepository extends JpaRepository<ProjectModel, Long> {
    Optional<ProjectModel> findByClientAndTitle(String client, String title);
}

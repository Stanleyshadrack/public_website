package com.example.company.modules.career.infra;


import com.example.company.modules.career.domain.entity.JobApplicationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobApplicationJpaRepository extends JpaRepository<JobApplicationModel, Long> {
    Optional<JobApplicationModel> findByJobIdAndEmail(Long jobId, String email);
}


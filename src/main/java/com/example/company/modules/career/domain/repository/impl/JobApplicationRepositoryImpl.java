package com.example.company.modules.career.domain.repository.impl;

import com.example.company.modules.career.domain.entity.JobApplicationModel;
import com.example.company.modules.career.domain.repository.JobApplicationRepository;
import com.example.company.modules.career.infra.JobApplicationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JobApplicationRepositoryImpl implements JobApplicationRepository {

    private final JobApplicationJpaRepository jpa;

    public JobApplicationModel saveApplication(JobApplicationModel app) {
        return jpa.save(app);
    }

    @Override
    public Optional<JobApplicationModel> findByJobIdAndEmail(Long jobId, String email) {
        return jpa.findByJobIdAndEmail(jobId, email);
    }

}


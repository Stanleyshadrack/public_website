package com.example.company.modules.career.domain.repository.impl;


import com.example.company.modules.career.domain.entity.JobModel;
import com.example.company.modules.career.domain.repository.JobRepository;
import com.example.company.modules.career.infra.JobJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JobRepositoryImpl implements JobRepository {

    private final JobJpaRepository jpa;

    public List<JobModel> getAllJobs() { return jpa.findAll(); }
    public Optional<JobModel> getJob(Long id) { return jpa.findById(id); }
    public JobModel saveJob(JobModel job) { return jpa.save(job); }
    public void deleteJob(JobModel job) { jpa.delete(job); }
}


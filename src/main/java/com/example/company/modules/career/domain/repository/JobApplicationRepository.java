package com.example.company.modules.career.domain.repository;

import com.example.company.modules.career.domain.entity.JobApplicationModel;
import java.util.Optional;

public interface JobApplicationRepository {
    JobApplicationModel saveApplication(JobApplicationModel application);
    Optional<JobApplicationModel> findByJobIdAndEmail(Long jobId, String email);

}


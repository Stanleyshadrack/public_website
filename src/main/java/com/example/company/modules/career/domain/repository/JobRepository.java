package com.example.company.modules.career.domain.repository;


import com.example.company.modules.career.domain.entity.JobModel;
import java.util.List;
import java.util.Optional;

public interface JobRepository {
    List<JobModel> getAllJobs();
    Optional<JobModel> getJob(Long id);
    JobModel saveJob(JobModel job);
    void deleteJob(JobModel job);
}

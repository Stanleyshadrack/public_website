package com.example.company.modules.career.app.service;


import com.example.company.modules.career.app.dto.JobDTO;
import com.example.company.modules.career.app.dto.JobApplicationDTO;
import java.util.List;

public interface CareerService {
    List<JobDTO> getAllJobs();
    JobDTO getJob(Long id);
    JobDTO createJob(JobDTO dto);
    JobDTO updateJob(Long id, JobDTO dto);
    void deleteJob(Long id);
    void applyForJob(JobApplicationDTO dto);
}


package com.example.company.modules.career.app.service.impl;

import com.example.company.modules.career.app.dto.JobApplicationDTO;
import com.example.company.modules.career.app.dto.JobDTO;
import com.example.company.modules.career.app.service.CareerService;
import com.example.company.modules.career.domain.entity.JobApplicationModel;
import com.example.company.modules.career.domain.entity.JobModel;
import com.example.company.modules.career.domain.repository.JobApplicationRepository;
import com.example.company.modules.career.domain.repository.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CareerServiceImpl implements CareerService {

    private final JobRepository jobRepo;
    private final JobApplicationRepository appRepo;

    public CareerServiceImpl(JobRepository jobRepo, JobApplicationRepository appRepo) {
        this.jobRepo = jobRepo;
        this.appRepo = appRepo;
    }

    public List<JobDTO> getAllJobs() {
        return jobRepo.getAllJobs().stream()
                .map(j -> new JobDTO(j.getId(), j.getTitle(), j.getDescription(), j.getLocation(), j.getQualification()))
                .toList();
    }

    public JobDTO getJob(Long id) {
        JobModel j = jobRepo.getJob(id).orElseThrow(() -> new RuntimeException("Job not found"));
        return new JobDTO(j.getId(), j.getTitle(), j.getDescription(), j.getLocation(), j.getQualification());
    }

    public JobDTO createJob(JobDTO dto) {
        JobModel saved = jobRepo.saveJob(new JobModel(dto.getTitle(), dto.getDescription(), dto.getLocation(), dto.getQualification()));
        return new JobDTO(saved.getId(), saved.getTitle(), saved.getDescription(), saved.getLocation(), saved.getQualification());
    }

    public JobDTO updateJob(Long id, JobDTO dto) {
        JobModel j = jobRepo.getJob(id).orElseThrow(() -> new RuntimeException("Job not found"));

        if (dto.getTitle() != null) j.setTitle(dto.getTitle());
        if (dto.getDescription() != null) j.setDescription(dto.getDescription());
        if (dto.getLocation() != null) j.setLocation(dto.getLocation());
        if (dto.getQualification() != null) j.setQualification(dto.getQualification());

        JobModel saved = jobRepo.saveJob(j);
        return new JobDTO(saved.getId(), saved.getTitle(), saved.getDescription(), saved.getLocation(), saved.getQualification());
    }

    public void deleteJob(Long id) {
        JobModel j = jobRepo.getJob(id).orElseThrow(() -> new RuntimeException("Job not found"));
        jobRepo.deleteJob(j);
    }

    @Override
    public void applyForJob(JobApplicationDTO dto) {

        // ✅ Ensure job exists
        jobRepo.getJob(dto.getJobId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job not found"));

        // ✅ Check if this email already applied to this job
        boolean alreadyApplied = appRepo.findByJobIdAndEmail(dto.getJobId(), dto.getEmail()).isPresent();
        if (alreadyApplied) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You have already applied for this job"
            );
        }

        JobApplicationModel app = new JobApplicationModel(
                null,
                dto.getJobId(),
                dto.getFullName(),
                dto.getEmail(),
                dto.getResumeBase64(),
                dto.getMessage()
        );

        appRepo.saveApplication(app);
    }


}


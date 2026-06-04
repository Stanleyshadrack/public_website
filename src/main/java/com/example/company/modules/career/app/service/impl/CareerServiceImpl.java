package com.example.company.modules.career.app.service.impl;

import com.example.company.exceptions.BadRequestException;
import com.example.company.exceptions.NotFoundException;
import com.example.company.modules.career.app.dto.JobApplicationDTO;
import com.example.company.modules.career.app.dto.JobDTO;
import com.example.company.modules.career.app.mapper.CareerMapper;
import com.example.company.modules.career.app.service.CareerService;
import com.example.company.modules.career.domain.entity.JobApplicationModel;
import com.example.company.modules.career.domain.entity.JobModel;
import com.example.company.modules.career.domain.repository.JobApplicationRepository;
import com.example.company.modules.career.domain.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerServiceImpl implements CareerService {

    private final JobRepository jobRepo;
    private final JobApplicationRepository appRepo;
    private final CareerMapper mapper;

    public CareerServiceImpl(JobRepository jobRepo,
                             JobApplicationRepository appRepo,
                             CareerMapper mapper) {
        this.jobRepo = jobRepo;
        this.appRepo = appRepo;
        this.mapper = mapper;
    }

    // ===== JOBS =====

    @Override
    public List<JobDTO> getAllJobs() {
        return jobRepo.getAllJobs()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public JobDTO getJob(Long id) {
        JobModel job = jobRepo.getJob(id)
                .orElseThrow(() -> new NotFoundException("Job not found"));

        return mapper.toDTO(job);
    }

    @Override
    public JobDTO createJob(JobDTO dto) {

        validateJob(dto);

        String title = dto.getTitle().trim();

        boolean exists = jobRepo.existsByTitleIgnoreCase(title);
        if (exists) {
            throw new BadRequestException("A job with this title already exists");
        }

        dto.setTitle(title); // normalize before saving

        JobModel job = mapper.toEntity(dto);
        JobModel saved = jobRepo.saveJob(job);

        return mapper.toDTO(saved);
    }

    @Override
    public JobDTO updateJob(Long id, JobDTO dto) {

        JobModel job = jobRepo.getJob(id)
                .orElseThrow(() -> new NotFoundException("Job not found"));

        // Handle title uniqueness ONLY if title is being updated
        if (dto.getTitle() != null) {

            String newTitle = dto.getTitle().trim();

            if (!newTitle.equalsIgnoreCase(job.getTitle())) {

                boolean exists = jobRepo.existsByTitleIgnoreCase(newTitle);
                if (exists) {
                    throw new BadRequestException("A job with this title already exists");
                }
            }

            dto.setTitle(newTitle);
        }

        mapper.updateEntity(job, dto);

        JobModel saved = jobRepo.saveJob(job);
        return mapper.toDTO(saved);
    }

    @Override
    public void deleteJob(Long id) {
        JobModel job = jobRepo.getJob(id)
                .orElseThrow(() -> new NotFoundException("Job not found"));

        jobRepo.deleteJob(job);
    }

    // ===== APPLICATION =====

    @Override
    public void applyForJob(JobApplicationDTO dto) {

        validateApplication(dto);

        jobRepo.getJob(dto.getJobId())
                .orElseThrow(() -> new NotFoundException("Job not found"));

        boolean alreadyApplied = appRepo
                .findByJobIdAndEmail(dto.getJobId(), dto.getEmail())
                .isPresent();

        if (alreadyApplied) {
            throw new BadRequestException("You have already applied for this job");
        }

        JobApplicationModel app = mapper.toEntity(dto);
        appRepo.saveApplication(app);
    }

    // ===== VALIDATIONS =====

    private void validateJob(JobDTO dto) {

        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new BadRequestException("Job title is required");
        }

        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
            throw new BadRequestException("Job description is required");
        }

        if (dto.getLocation() == null || dto.getLocation().trim().isEmpty()) {
            throw new BadRequestException("Job location is required");
        }

        if (dto.getQualification() == null || dto.getQualification().trim().isEmpty()) {
            throw new BadRequestException("Qualification is required");
        }
    }

    private void validateApplication(JobApplicationDTO dto) {
        if (dto.getJobId() == null) {
            throw new BadRequestException("Job ID is required");
        }
        if (dto.getFirstName() == null || dto.getFirstName().isBlank()) {
            throw new BadRequestException("First name is required");
        }
        if (dto.getLastName() == null || dto.getLastName().isBlank()) {
            throw new BadRequestException("Last name is required");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new BadRequestException("Email is required");
        }
        if (dto.getResumeUrl() == null || dto.getResumeUrl().isBlank()) {
            throw new BadRequestException("Resume is required");
        }
    }
}
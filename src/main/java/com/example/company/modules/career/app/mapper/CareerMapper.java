package com.example.company.modules.career.app.mapper;

import com.example.company.modules.career.app.dto.JobApplicationDTO;
import com.example.company.modules.career.app.dto.JobDTO;
import com.example.company.modules.career.domain.entity.JobApplicationModel;
import com.example.company.modules.career.domain.entity.JobModel;
import org.springframework.stereotype.Component;

@Component
public class CareerMapper {

    // ===== JOB =====
    public JobDTO toDTO(JobModel j) {
        if (j == null) return null;

        return new JobDTO(
                j.getId(),
                j.getTitle(),
                j.getDescription(),
                j.getLocation(),
                j.getQualification(),
                j.getDuties(),
                j.getRequirements()
        );
    }

    public JobModel toEntity(JobDTO dto) {
        if (dto == null) return null;

        JobModel job = new JobModel();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setQualification(dto.getQualification());
        job.setDuties(dto.getDuties());
        job.setRequirements(dto.getRequirements());
        return job;
    }

    public void updateEntity(JobModel job, JobDTO dto) {
        if (dto.getTitle() != null) job.setTitle(dto.getTitle());
        if (dto.getDescription() != null) job.setDescription(dto.getDescription());
        if (dto.getLocation() != null) job.setLocation(dto.getLocation());
        if (dto.getQualification() != null) job.setQualification(dto.getQualification());
        if (dto.getDuties() != null) job.setDuties(dto.getDuties());
        if (dto.getRequirements() != null) job.setRequirements(dto.getRequirements());
    }

    // ===== APPLICATION =====
    public JobApplicationModel toEntity(JobApplicationDTO dto) {
        JobApplicationModel app = new JobApplicationModel();

        app.setJobId(dto.getJobId());
        app.setJobTitle(dto.getJobTitle());
        app.setFirstName(dto.getFirstName());
        app.setLastName(dto.getLastName());
        app.setEmail(dto.getEmail());
        app.setPhone(dto.getPhone());
        app.setLinkedin(dto.getLinkedin());
        app.setPortfolio(dto.getPortfolio());
        app.setExperience(dto.getExperience());
        app.setMotivation(dto.getMotivation());
        app.setReferral(dto.getReferral());
        app.setResumeUrl(dto.getResumeUrl());
        app.setCoverLetterUrl(dto.getCoverLetterUrl());

        return app;
    }


}
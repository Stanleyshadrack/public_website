package com.example.company.modules.career.infra;


import com.example.company.modules.career.domain.entity.JobModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobJpaRepository extends JpaRepository<JobModel, Long> {}


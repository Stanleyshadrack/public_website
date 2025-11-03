package com.example.company.modules.career.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "job_applications")
public class JobApplicationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;
    private String fullName;
    private String email;
    private String message;

    @Column(columnDefinition = "TEXT")
    private String resumeBase64; // later, we can switch to file upload
}

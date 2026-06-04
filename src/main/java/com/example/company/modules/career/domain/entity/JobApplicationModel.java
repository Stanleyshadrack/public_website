package com.example.company.modules.career.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_applications")
public class JobApplicationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;
    private String jobTitle;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private String linkedin;
    private String portfolio;

    @Column(columnDefinition = "TEXT")
    private String experience;

    @Column(columnDefinition = "TEXT")
    private String motivation;

    private String referral;

    // File storage (recommended: store URL, not file itself)
    private String resumeUrl;
    private String coverLetterUrl;
}
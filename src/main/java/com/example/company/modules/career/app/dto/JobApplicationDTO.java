package com.example.company.modules.career.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDTO {

    private Long jobId;
    private String jobTitle;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private String linkedin;
    private String portfolio;

    private String experience;
    private String motivation;

    private String referral;

    private String resumeUrl;
    private String coverLetterUrl;
}
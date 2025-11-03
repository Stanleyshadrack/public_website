package com.example.company.modules.career.app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDTO {
    private Long jobId;
    private String fullName;
    private String email;
    private String resumeBase64;
    private String message;
}
package com.example.company.modules.policy.app.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDTO {
    private Long id;
    private String title;
    private String description;
    private Object content; // can be string or list
    private String type;    // paragraph or list
}


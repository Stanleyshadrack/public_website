package com.example.company.modules.career.app.dto;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String qualification;
    private List<String> duties;
    private List<String> requirements;
}


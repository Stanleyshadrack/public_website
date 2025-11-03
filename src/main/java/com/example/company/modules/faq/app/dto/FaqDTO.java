package com.example.company.modules.faq.app.dto;



import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaqDTO {
    private Long id;
    private String question;
    private String answer;
}


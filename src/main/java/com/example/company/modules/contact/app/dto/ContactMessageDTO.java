package com.example.company.modules.contact.app.dto;

;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String subject;
    private String message;
}

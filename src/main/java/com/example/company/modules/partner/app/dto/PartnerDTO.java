package com.example.company.modules.partner.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDTO {
    private Long id;
    private String imageBase64;
    private String label;
}

package com.example.company.modules.partner.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partners")
public class PartnerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] image;

    private String label;

    // ✅ Custom constructor for creation
    public PartnerModel(byte[] image, String label) {
        this.image = image;
        this.label = label;
    }
}

package com.example.company.modules.project.domain.entity;

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
@Table(name = "projects")
public class ProjectModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] image;

    private String title;
    private String description;

    // Optional custom constructor
    public ProjectModel(byte[] image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;

    }
}

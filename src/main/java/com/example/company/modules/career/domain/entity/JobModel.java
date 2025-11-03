package com.example.company.modules.career.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "jobs")
public class JobModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location;
    private String qualification;

    public JobModel(String title, String description, String location, String qualification) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.qualification = qualification;
    }
}

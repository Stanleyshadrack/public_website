package com.example.company.modules.career.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobs")
public class JobModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location;

    private String qualification;

    @ElementCollection
    @CollectionTable(name = "job_duties", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "duty")
    private List<String> duties;

    @ElementCollection
    @CollectionTable(name = "job_requirements", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "requirement")
    private List<String> requirements;

    public JobModel(String title, String description, String location, String qualification) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.qualification = qualification;
    }
}
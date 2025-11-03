package com.example.company.modules.policy.domain.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "policies")
public class PolicyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(columnDefinition = "TEXT")
    private String content; // we will store JSON or text

    private String type; // paragraph | list

    public PolicyModel(String title, String description, String content, String type) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.type = type;
    }
}


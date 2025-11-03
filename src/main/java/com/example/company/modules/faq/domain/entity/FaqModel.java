package com.example.company.modules.faq.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "faqs")
@Getter
@Setter
@NoArgsConstructor // ✅ required by JPA
@AllArgsConstructor
public class FaqModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    // ✅ constructor for creating new FAQ (without ID)
    public FaqModel(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}

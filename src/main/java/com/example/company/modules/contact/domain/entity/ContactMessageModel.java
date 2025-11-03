package com.example.company.modules.contact.domain.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact_messages")
public class ContactMessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;

    public ContactMessageModel(String firstName, String lastName, String email, String subject, String message) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }
}

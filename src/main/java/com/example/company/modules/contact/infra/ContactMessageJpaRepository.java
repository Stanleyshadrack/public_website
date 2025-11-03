package com.example.company.modules.contact.infra;



import com.example.company.modules.contact.domain.entity.ContactMessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageJpaRepository extends JpaRepository<ContactMessageModel, Long> {}


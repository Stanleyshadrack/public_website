package com.example.company.modules.contact.domain.repository.impl;



import com.example.company.modules.contact.domain.entity.ContactMessageModel;
import com.example.company.modules.contact.domain.repository.ContactMessageRepository;
import com.example.company.modules.contact.infra.ContactMessageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContactMessageRepositoryImpl implements ContactMessageRepository {

    private final ContactMessageJpaRepository jpa;

    @Override
    public ContactMessageModel save(ContactMessageModel message) {
        return jpa.save(message);
    }

    @Override
    public List<ContactMessageModel> getAll() {
        return jpa.findAll();
    }

    @Override
    public Optional<ContactMessageModel> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public void delete(ContactMessageModel message) {
        jpa.delete(message);
    }
}


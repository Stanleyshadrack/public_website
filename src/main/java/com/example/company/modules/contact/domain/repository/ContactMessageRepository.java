package com.example.company.modules.contact.domain.repository;

import com.example.company.modules.contact.domain.entity.ContactMessageModel;
import java.util.List;
import java.util.Optional;

public interface ContactMessageRepository {
    ContactMessageModel save(ContactMessageModel message);
    List<ContactMessageModel> getAll();
    Optional<ContactMessageModel> findById(Long id);
    void delete(ContactMessageModel message);
}

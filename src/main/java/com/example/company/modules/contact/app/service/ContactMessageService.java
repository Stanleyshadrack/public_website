package com.example.company.modules.contact.app.service;


import com.example.company.modules.contact.app.dto.ContactMessageDTO;
import java.util.List;

public interface ContactMessageService {
    ContactMessageDTO submit(ContactMessageDTO dto);
    List<ContactMessageDTO> getAll();
    ContactMessageDTO getMessage(Long id);
    void deleteMessage(Long id);
    ContactMessageDTO updateMessage(Long id, ContactMessageDTO dto);
}


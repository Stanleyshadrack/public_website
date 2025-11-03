package com.example.company.modules.contact.app.service.impl;



import com.example.company.modules.contact.app.dto.ContactMessageDTO;
import com.example.company.modules.contact.app.service.ContactMessageService;
import com.example.company.modules.contact.domain.entity.ContactMessageModel;
import com.example.company.modules.contact.domain.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository repo;

    @Override
    public ContactMessageDTO submit(ContactMessageDTO dto) {
        ContactMessageModel saved = repo.save(new ContactMessageModel(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getSubject(),
                dto.getMessage()
        ));

        return new ContactMessageDTO(
                saved.getId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getEmail(),
                saved.getSubject(),
                saved.getMessage()
        );
    }

    @Override
    public List<ContactMessageDTO> getAll() {
        return repo.getAll()
                .stream()
                .map(m -> new ContactMessageDTO(
                        m.getId(),
                        m.getFirstName(),
                        m.getLastName(),
                        m.getEmail(),
                        m.getSubject(),
                        m.getMessage()
                )).toList();
    }

    @Override
    public ContactMessageDTO getMessage(Long id) {
        ContactMessageModel msg = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));

        return new ContactMessageDTO(
                msg.getId(),
                msg.getFirstName(),
                msg.getLastName(),
                msg.getEmail(),
                msg.getSubject(),
                msg.getMessage()
        );
    }

    @Override
    public void deleteMessage(Long id) {
        ContactMessageModel msg = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));
        repo.delete(msg);
    }

    @Override
    public ContactMessageDTO updateMessage(Long id, ContactMessageDTO dto) {
        ContactMessageModel msg = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));

        if (dto.getFirstName() != null && !dto.getFirstName().isEmpty()) {
            msg.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null && !dto.getLastName().isEmpty()) {
            msg.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            msg.setEmail(dto.getEmail());
        }
        if (dto.getSubject() != null && !dto.getSubject().isEmpty()) {
            msg.setSubject(dto.getSubject());
        }
        if (dto.getMessage() != null && !dto.getMessage().isEmpty()) {
            msg.setMessage(dto.getMessage());
        }

        ContactMessageModel updated = repo.save(msg);

        return new ContactMessageDTO(
                updated.getId(),
                updated.getFirstName(),
                updated.getLastName(),
                updated.getEmail(),
                updated.getSubject(),
                updated.getMessage()
        );
    }

}

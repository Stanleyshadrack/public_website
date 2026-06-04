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

        //  VALIDATION
        if (dto.getFirstName() == null || dto.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (dto.getLastName() == null || dto.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (dto.getSubject() == null || dto.getSubject().isBlank()) {
            throw new IllegalArgumentException("Subject is required");
        }
        if (dto.getMessage() == null || dto.getMessage().isBlank()) {
            throw new IllegalArgumentException("Message text is required");
        }

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
                ))
                .toList();
    }

    @Override
    public ContactMessageDTO getMessage(Long id) {
        ContactMessageModel msg = repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found")
                );

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
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found")
                );

        repo.delete(msg);
    }

    @Override
    public ContactMessageDTO updateMessage(Long id, ContactMessageDTO dto) {
        ContactMessageModel msg = repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found")
                );

        // UPDATE ONLY VALID FIELDS
        if (dto.getFirstName() != null) {
            if (dto.getFirstName().isBlank()) {
                throw new IllegalArgumentException("First name cannot be empty");
            }
            msg.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null) {
            if (dto.getLastName().isBlank()) {
                throw new IllegalArgumentException("Last name cannot be empty");
            }
            msg.setLastName(dto.getLastName());
        }

        if (dto.getEmail() != null) {
            if (dto.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }
            msg.setEmail(dto.getEmail());
        }

        if (dto.getSubject() != null) {
            if (dto.getSubject().isBlank()) {
                throw new IllegalArgumentException("Subject cannot be empty");
            }
            msg.setSubject(dto.getSubject());
        }

        if (dto.getMessage() != null) {
            if (dto.getMessage().isBlank()) {
                throw new IllegalArgumentException("Message text cannot be empty");
            }
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

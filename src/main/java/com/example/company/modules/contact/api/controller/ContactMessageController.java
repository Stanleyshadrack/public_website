package com.example.company.modules.contact.api.controller;


import com.example.company.modules.contact.app.dto.ContactMessageDTO;
import com.example.company.modules.contact.app.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/V1/api/contact")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService service;

    @PostMapping
    public ContactMessageDTO submit(@RequestBody ContactMessageDTO dto) {
        return service.submit(dto);
    }

    @GetMapping
    public List<ContactMessageDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ContactMessageDTO getOne(@PathVariable Long id) {
        return service.getMessage(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ContactMessageDTO update(@PathVariable Long id, @RequestBody ContactMessageDTO dto) {
        return service.updateMessage(id, dto);
    }

}


package com.example.company.modules.contact.api.controller;

import com.example.company.modules.contact.app.dto.ContactMessageDTO;
import com.example.company.modules.contact.app.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/contact")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService service;

    // ✅ Public endpoint
    @PostMapping
    public ContactMessageDTO submit(@RequestBody ContactMessageDTO dto) {
        return service.submit(dto);
    }

    // 🔒 Protected endpoints (for internal/admin use)
    @GetMapping("/ops")
    public List<ContactMessageDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/ops/{id}")
    public ContactMessageDTO getOne(@PathVariable Long id) {
        return service.getMessage(id);
    }

    @PutMapping("/ops/{id}")
    public ContactMessageDTO update(@PathVariable Long id, @RequestBody ContactMessageDTO dto) {
        return service.updateMessage(id, dto);
    }

    @DeleteMapping("/ops/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteMessage(id);
        return ResponseEntity.ok("{\"message\": \"Message deleted successfully\"}");
    }

}

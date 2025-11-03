package com.example.company.modules.faq.api.controller;

import com.example.company.modules.faq.app.dto.FaqDTO;
import com.example.company.modules.faq.app.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/V1/api/faqs")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService service;

    @GetMapping
    public List<FaqDTO> getAllFaqs() {
        return service.getAllFaqs();
    }

    @GetMapping("/{id}")
    public FaqDTO getFaq(@PathVariable Long id) {
        return service.getFaq(id);
    }

    @PostMapping
    public FaqDTO createFaq(@RequestBody FaqDTO dto) {
        return service.createFaq(dto);
    }

    @PutMapping("/{id}")
    public FaqDTO updateFaq(@PathVariable Long id, @RequestBody FaqDTO dto) {
        return service.updateFaq(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long id) {
        service.deleteFaq(id);
        return ResponseEntity.noContent().build();
    }
}

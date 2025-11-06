package com.example.company.modules.partner.api.conroller;

import com.example.company.modules.partner.app.dto.PartnerDTO;
import com.example.company.modules.partner.app.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/V1/api/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    // ✅ Public: Fetch all partners (e.g., displayed on the website)
    @GetMapping
    public ResponseEntity<List<PartnerDTO>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    // 🔒 Internal: Create partner (only backend can call this)
    @PostMapping("/manage")
    public ResponseEntity<PartnerDTO> createPartner(@RequestBody PartnerDTO request) {
        PartnerDTO created = partnerService.createPartner(request);
        return ResponseEntity.ok(created);
    }

    // 🔒 Optional future endpoints for management
    @PutMapping("/manage/{id}")
    public ResponseEntity<PartnerDTO> updatePartner(@PathVariable Long id, @RequestBody PartnerDTO request) {
        PartnerDTO updated = partnerService.updatePartner(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/manage/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.company.modules.partner.api.conroller;

import com.example.company.modules.partner.app.dto.PartnerDTO;
import com.example.company.modules.partner.app.service.PartnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    // Public: visible on website
    @GetMapping
    public ResponseEntity<List<PartnerDTO>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    // Internal: Create partner
    @PostMapping("/manage")
    public ResponseEntity<PartnerDTO> createPartner(@Valid @RequestBody PartnerDTO request) {
        PartnerDTO created = partnerService.createPartner(request);
        return ResponseEntity.ok(created);
    }

    // Internal: Update partner
    @PutMapping("/manage/{id}")
    public ResponseEntity<PartnerDTO> updatePartner(
            @PathVariable Long id,
            @Valid @RequestBody PartnerDTO request
    ) {
        PartnerDTO updated = partnerService.updatePartner(id, request);
        return ResponseEntity.ok(updated);
    }

    //  Internal: Delete partner
    @DeleteMapping("/manage/{id}")
    public ResponseEntity<?> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.ok().body(
                "{\"message\":\"Partner deleted successfully\"}"
        );
    }
}

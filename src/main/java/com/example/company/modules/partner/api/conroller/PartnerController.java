package com.example.company.modules.partner.api.conroller;



import com.example.company.modules.partner.app.dto.PartnerDTO;
import com.example.company.modules.partner.app.service.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/V1/api/partners")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping
    public ResponseEntity<List<PartnerDTO>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @PostMapping
    public ResponseEntity<PartnerDTO> createPartner(@RequestBody PartnerDTO request) {
        PartnerDTO created = partnerService.createPartner(request);
        return ResponseEntity.ok(created);
    }
}

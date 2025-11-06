package com.example.company.modules.partner.app.service;

import com.example.company.modules.partner.app.dto.PartnerDTO;
import java.util.List;

public interface PartnerService {
    List<PartnerDTO> getAllPartners();
    PartnerDTO createPartner(PartnerDTO partnerDTO);
    PartnerDTO updatePartner(Long id, PartnerDTO partnerDTO);
    void deletePartner(Long id);
}

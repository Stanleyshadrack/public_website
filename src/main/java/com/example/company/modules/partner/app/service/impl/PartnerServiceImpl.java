package com.example.company.modules.partner.app.service.impl;

import com.example.company.modules.partner.app.dto.PartnerDTO;
import com.example.company.modules.partner.app.service.PartnerService;
import com.example.company.modules.partner.domain.entity.PartnerModel;
import com.example.company.modules.partner.domain.repository.PartnerRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerServiceImpl(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    public List<PartnerDTO> getAllPartners() {
        return partnerRepository.getAllPartners()
                .stream()
                .map(p -> new PartnerDTO(
                        p.getId(),
                        Base64.getEncoder().encodeToString(p.getImage()), // ✅ convert bytes to Base64
                        p.getLabel()
                ))
                .toList();
    }

    @Override
    public PartnerDTO createPartner(PartnerDTO dto) {
        // ✅ decode base64 image string to byte[]
        byte[] imageBytes = Base64.getDecoder().decode(dto.getImageBase64());

        PartnerModel partner = new PartnerModel(imageBytes, dto.getLabel());
        PartnerModel saved = partnerRepository.savePartner(partner);

        return new PartnerDTO(
                saved.getId(),
                Base64.getEncoder().encodeToString(saved.getImage()),  // ✅ return as Base64
                saved.getLabel()
        );
    }

}

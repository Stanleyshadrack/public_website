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

    @Override
    public PartnerDTO updatePartner(Long id, PartnerDTO dto) {
        PartnerModel existing = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with ID: " + id));

        // ✅ Update label
        existing.setLabel(dto.getLabel());

        // ✅ Update image only if a new one was provided
        if (dto.getImageBase64() != null && !dto.getImageBase64().isBlank()) {
            byte[] newImage = Base64.getDecoder().decode(dto.getImageBase64());
            existing.setImage(newImage);
        }

        PartnerModel updated = partnerRepository.savePartner(existing);

        return new PartnerDTO(
                updated.getId(),
                Base64.getEncoder().encodeToString(updated.getImage()),
                updated.getLabel()
        );
    }

    @Override
    public void deletePartner(Long id) {
        PartnerModel existing = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with ID: " + id));

        partnerRepository.delete(existing);
    }
}


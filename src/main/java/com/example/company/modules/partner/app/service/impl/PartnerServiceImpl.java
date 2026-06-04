package com.example.company.modules.partner.app.service.impl;

import com.example.company.modules.partner.app.dto.PartnerDTO;
import com.example.company.modules.partner.app.service.PartnerService;
import com.example.company.modules.partner.domain.entity.PartnerModel;
import com.example.company.modules.partner.domain.repository.PartnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                        Base64.getEncoder().encodeToString(p.getImage()),
                        p.getLabel()
                ))
                .toList();
    }

    @Override
    public PartnerDTO createPartner(PartnerDTO dto) {

        if (dto.getLabel() == null || dto.getLabel().isBlank()) {
            throw new IllegalArgumentException("Partner label is required.");
        }

        if (dto.getImageBase64() == null || dto.getImageBase64().isBlank()) {
            throw new IllegalArgumentException("Image base64 data is required.");
        }

        byte[] imageBytes;
        try {
            imageBytes = Base64.getDecoder().decode(dto.getImageBase64());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Base64 image data.");
        }

        PartnerModel partner = new PartnerModel(imageBytes, dto.getLabel());
        PartnerModel saved = partnerRepository.savePartner(partner);

        return new PartnerDTO(
                saved.getId(),
                Base64.getEncoder().encodeToString(saved.getImage()),
                saved.getLabel()
        );
    }

    @Override
    public PartnerDTO updatePartner(Long id, PartnerDTO dto) {

        PartnerModel existing = partnerRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Partner not found with ID: " + id)
                );

        // Update label
        if (dto.getLabel() != null && !dto.getLabel().isBlank()) {
            existing.setLabel(dto.getLabel());
        }

        // Update image Base64 only if provided
        if (dto.getImageBase64() != null && !dto.getImageBase64().isBlank()) {

            try {
                byte[] newImage = Base64.getDecoder().decode(dto.getImageBase64());
                existing.setImage(newImage);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid Base64 image data.");
            }

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
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Partner not found with ID: " + id)
                );

        partnerRepository.delete(existing);
    }
}

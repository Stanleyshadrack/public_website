package com.example.company.modules.partner.domain.repository;

import com.example.company.modules.partner.domain.entity.PartnerModel;
import java.util.List;
import java.util.Optional;

public interface PartnerRepository {

    List<PartnerModel> getAllPartners();

    PartnerModel savePartner(PartnerModel partner);

    Optional<PartnerModel> findById(Long id);

    void delete(PartnerModel partner);
}

package com.example.company.modules.partner.domain.repository.impl;

import com.example.company.modules.partner.domain.entity.PartnerModel;
import com.example.company.modules.partner.domain.repository.PartnerRepository;
import com.example.company.modules.partner.infra.jpaRepository.PartnerJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PartnerRepositoryImpl implements PartnerRepository {

    private final PartnerJpaRepository jpaRepo;

    public PartnerRepositoryImpl(PartnerJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public List<PartnerModel> getAllPartners() {
        return List.of();
    }

    @Override
    public PartnerModel savePartner(PartnerModel partner) {
        return jpaRepo.save(partner);
    }
}

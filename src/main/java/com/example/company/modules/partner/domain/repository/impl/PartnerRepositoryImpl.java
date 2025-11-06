package com.example.company.modules.partner.domain.repository.impl;

import com.example.company.modules.partner.domain.entity.PartnerModel;
import com.example.company.modules.partner.domain.repository.PartnerRepository;
import com.example.company.modules.partner.infra.jpaRepository.PartnerJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PartnerRepositoryImpl implements PartnerRepository {

    private final PartnerJpaRepository jpaRepo;

    public PartnerRepositoryImpl(PartnerJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public List<PartnerModel> getAllPartners() {
        // ✅ Retrieve all partners from the JPA repository
        return jpaRepo.findAll();
    }

    @Override
    public PartnerModel savePartner(PartnerModel partner) {
        return jpaRepo.save(partner);
    }

    @Override
    public Optional<PartnerModel> findById(Long id) {
        return jpaRepo.findById(id);
    }

    @Override
    public void delete(PartnerModel partner) {
        jpaRepo.delete(partner);
    }
}

package com.example.company.modules.partner.infra.jpaRepository;



import com.example.company.modules.partner.domain.entity.PartnerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerJpaRepository extends JpaRepository<PartnerModel, Long> { }


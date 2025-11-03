package com.example.company.modules.faq.infra;


import com.example.company.modules.faq.domain.entity.FaqModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqJpaRepository extends JpaRepository<FaqModel, Long> {
}


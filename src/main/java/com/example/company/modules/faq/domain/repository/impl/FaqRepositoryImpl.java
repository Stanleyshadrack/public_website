package com.example.company.modules.faq.domain.repository.impl;

import com.example.company.modules.faq.domain.entity.FaqModel;
import com.example.company.modules.faq.domain.repository.FaqRepository;
import com.example.company.modules.faq.infra.FaqJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FaqRepositoryImpl implements FaqRepository {

    private final FaqJpaRepository jpa;

    @Override
    public List<FaqModel> getAllFaqs() {
        return jpa.findAll();
    }

    @Override
    public Optional<FaqModel> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public FaqModel saveFaq(FaqModel faq) {
        return jpa.save(faq);
    }

    @Override
    public void deleteFaq(FaqModel faq) {
        jpa.delete(faq);
    }
}

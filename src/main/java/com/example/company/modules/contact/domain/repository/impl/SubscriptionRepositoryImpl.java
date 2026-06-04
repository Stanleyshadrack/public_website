package com.example.company.modules.contact.domain.repository.impl;

import com.example.company.modules.contact.domain.entity.SubscriptionModel;
import com.example.company.modules.contact.domain.repository.SubscriptionRepository;
import com.example.company.modules.contact.infra.SubscriptionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SubscriptionJpaRepository jpa;

    public SubscriptionRepositoryImpl(SubscriptionJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public SubscriptionModel save(SubscriptionModel subscription) {
        return jpa.save(subscription);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }

    @Override
    public Optional<SubscriptionModel> findByEmail(String email) {
        return jpa.findByEmail(email);
    }

    @Override
    public List<SubscriptionModel> findAll() {
        return jpa.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}

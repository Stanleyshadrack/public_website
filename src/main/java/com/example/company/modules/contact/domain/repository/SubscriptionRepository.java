package com.example.company.modules.contact.domain.repository;

import com.example.company.modules.contact.domain.entity.SubscriptionModel;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    SubscriptionModel save(SubscriptionModel subscription);
    boolean existsByEmail(String email);
    Optional<SubscriptionModel> findByEmail(String email);
    List<SubscriptionModel> findAll();
    void deleteById(Long id);
}

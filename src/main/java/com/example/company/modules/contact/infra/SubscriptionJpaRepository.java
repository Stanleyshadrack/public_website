package com.example.company.modules.contact.infra;

import com.example.company.modules.contact.domain.entity.SubscriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionJpaRepository extends JpaRepository<SubscriptionModel, Long> {
    boolean existsByEmail(String email);
    java.util.Optional<SubscriptionModel> findByEmail(String email);
}

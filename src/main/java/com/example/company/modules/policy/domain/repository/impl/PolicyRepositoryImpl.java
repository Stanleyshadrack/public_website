package com.example.company.modules.policy.domain.repository.impl;

;

import com.example.company.modules.policy.domain.entity.PolicyModel;
import com.example.company.modules.policy.domain.repository.PolicyRepository;
import com.example.company.modules.policy.infra.PolicyJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PolicyRepositoryImpl implements PolicyRepository {

    private final PolicyJpaRepository jpa;

    @Override
    public List<PolicyModel> getAll() {
        return jpa.findAll();
    }

    @Override
    public Optional<PolicyModel> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public PolicyModel savePolicy(PolicyModel policy) {
        return jpa.save(policy);
    }

    @Override
    public void deletePolicy(PolicyModel policy) {
        jpa.delete(policy);
    }
}


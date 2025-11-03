package com.example.company.modules.policy.domain.repository;


import com.example.company.modules.policy.domain.entity.PolicyModel;
import java.util.List;
import java.util.Optional;

public interface PolicyRepository {
    List<PolicyModel> getAll();
    Optional<PolicyModel> findById(Long id);
    PolicyModel savePolicy(PolicyModel policy);
    void deletePolicy(PolicyModel policy);
}


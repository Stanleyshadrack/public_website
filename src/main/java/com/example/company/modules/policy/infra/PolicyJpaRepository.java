package com.example.company.modules.policy.infra;



import com.example.company.modules.policy.domain.entity.PolicyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyJpaRepository extends JpaRepository<PolicyModel, Long> {}

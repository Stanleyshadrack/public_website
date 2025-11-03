package com.example.company.modules.policy.app.service;


import com.example.company.modules.policy.app.dto.PolicyDTO;
import java.util.List;

public interface PolicyService {
    List<PolicyDTO> getAll();
    PolicyDTO get(Long id);
    PolicyDTO create(PolicyDTO dto);
    PolicyDTO update(Long id, PolicyDTO dto);
    void delete(Long id);
}


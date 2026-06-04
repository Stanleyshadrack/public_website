package com.example.company.modules.policy.app.service.impl;

import com.example.company.modules.policy.app.dto.PolicyDTO;
import com.example.company.modules.policy.app.service.PolicyService;
import com.example.company.modules.policy.domain.entity.PolicyModel;
import com.example.company.modules.policy.domain.repository.PolicyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository repo;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<PolicyDTO> getAll() {
        return repo.getAll().stream().map(p -> new PolicyDTO(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                parseContent(p.getContent()),
                p.getType()
        )).toList();
    }

    @Override
    public PolicyDTO get(Long id) {
        PolicyModel p = repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Policy not found")
                );

        return new PolicyDTO(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                parseContent(p.getContent()),
                p.getType()
        );
    }

    @Override
    public PolicyDTO create(PolicyDTO dto) {

        // 🔍 VALIDATION
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Policy title is required");
        }

        if (dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Policy description is required");
        }

        if (dto.getContent() == null) {
            throw new IllegalArgumentException("Policy content is required");
        }

        if (dto.getType() == null) {
            throw new IllegalArgumentException("Policy type is required");
        }

        // Convert structured "content" to JSON
        String contentJson = serializeContent(dto.getContent());

        PolicyModel saved = repo.savePolicy(new PolicyModel(
                dto.getTitle(),
                dto.getDescription(),
                contentJson,
                dto.getType()
        ));

        return get(saved.getId());
    }

    @Override
    public PolicyDTO update(Long id, PolicyDTO dto) {
        PolicyModel p = repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Policy not found")
                );

        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            p.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            p.setDescription(dto.getDescription());
        }

        if (dto.getContent() != null) {
            p.setContent(serializeContent(dto.getContent()));
        }

        if (dto.getType() != null) {
            p.setType(dto.getType());
        }

        PolicyModel updated = repo.savePolicy(p);
        return get(updated.getId());
    }

    @Override
    public void delete(Long id) {
        PolicyModel p = repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Policy not found")
                );

        repo.deletePolicy(p);
    }

    // 🔧 SAFE PARSE JSON → Object (list/map/etc)
    private Object parseContent(String content) {
        try {
            return mapper.readValue(content, Object.class);
        } catch (Exception e) {
            // content is not JSON, return raw
            return content;
        }
    }

    // 🔧 Convert object to JSON string
    private String serializeContent(Object content) {
        try {
            return mapper.writeValueAsString(content);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid content format: must be JSON-compatible");
        }
    }
}

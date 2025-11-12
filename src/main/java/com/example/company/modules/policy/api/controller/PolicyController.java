package com.example.company.modules.policy.api.controller;

import com.example.company.modules.policy.app.dto.PolicyDTO;
import com.example.company.modules.policy.app.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/api/policies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PolicyController {

    private static final Logger logger = LoggerFactory.getLogger(PolicyController.class);
    private final PolicyService service;

    // ✅ Public: Anyone can view policies
    @GetMapping
    public ResponseEntity<List<PolicyDTO>> getAll() {
        List<PolicyDTO> policies = service.getAll();
        return ResponseEntity.ok(policies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyDTO> get(@PathVariable Long id) {
        PolicyDTO policy = service.get(id);
        return ResponseEntity.ok(policy);
    }

    // 🔒 Internal: Manage policies (create, update, delete)
    @PostMapping("/manage")
    public ResponseEntity<PolicyDTO> create(@RequestBody PolicyDTO dto) {
        PolicyDTO created = service.create(dto);
        logger.info(" Policy created: {}", created.getTitle());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/manage/{id}")
    public ResponseEntity<PolicyDTO> update(@PathVariable Long id, @RequestBody PolicyDTO dto) {
        PolicyDTO updated = service.update(id, dto);
        logger.info(" Policy updated: {}", updated.getTitle());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/manage/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        logger.info("🗑Policy deleted with ID: {}", id);
        return ResponseEntity.ok("{\"message\": \"Policy deleted successfully.\"}");
    }
}

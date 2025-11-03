package com.example.company.modules.policy.api.controller;



import com.example.company.modules.policy.app.dto.PolicyDTO;
import com.example.company.modules.policy.app.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/V1/api/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService service;

    @GetMapping
    public List<PolicyDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PolicyDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public PolicyDTO create(@RequestBody PolicyDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public PolicyDTO update(@PathVariable Long id, @RequestBody PolicyDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}


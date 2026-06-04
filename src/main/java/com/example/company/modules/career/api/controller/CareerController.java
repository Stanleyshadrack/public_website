package com.example.company.modules.career.api.controller;

import com.example.company.modules.career.app.dto.JobApplicationDTO;
import com.example.company.modules.career.app.dto.JobDTO;
import com.example.company.modules.career.app.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/careers")
@RequiredArgsConstructor
public class CareerController {

    private final CareerService service;

    // ===== PUBLIC =====

    @GetMapping
    public ResponseEntity<List<JobDTO>> allJobs() {
        return ResponseEntity.ok(service.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> job(@PathVariable Long id) {
        return ResponseEntity.ok(service.getJob(id));
    }

    // ===== ADMIN / SECURED =====

    @PostMapping("/actions")
    public ResponseEntity<JobDTO> create(@RequestBody JobDTO dto) {
        return ResponseEntity.ok(service.createJob(dto));
    }

    @PutMapping("/actions/{id}")
    public ResponseEntity<JobDTO> update(@PathVariable Long id, @RequestBody JobDTO dto) {
        return ResponseEntity.ok(service.updateJob(id, dto));
    }

    @DeleteMapping("/actions/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteJob(id);
        return ResponseEntity.ok("Job deleted successfully");
    }

    // ===== APPLICATION =====

    @PostMapping("/actions/apply")
    public ResponseEntity<String> apply(@RequestBody JobApplicationDTO dto) {
        service.applyForJob(dto);
        return ResponseEntity.ok("Application submitted successfully");
    }
}
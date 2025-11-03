package com.example.company.modules.career.api.controller;

import com.example.company.modules.career.app.dto.JobApplicationDTO;
import com.example.company.modules.career.app.dto.JobDTO;
import com.example.company.modules.career.app.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/V1/api/careers")
@RequiredArgsConstructor
public class CareerController {

    private final CareerService service;

    @GetMapping
    public List<JobDTO> allJobs() { return service.getAllJobs(); }

    @GetMapping("/{id}")
    public JobDTO job(@PathVariable Long id) { return service.getJob(id); }

    @PostMapping
    public JobDTO create(@RequestBody JobDTO dto) { return service.createJob(dto); }

    @PutMapping("/{id}")
    public JobDTO update(@PathVariable Long id, @RequestBody JobDTO dto) { return service.updateJob(id, dto); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.deleteJob(id); }

    @PostMapping("/apply")
    public ResponseEntity<String> apply(@RequestBody JobApplicationDTO dto) {
        service.applyForJob(dto);
        return ResponseEntity.ok("Application submitted successfully ✅");
    }
}


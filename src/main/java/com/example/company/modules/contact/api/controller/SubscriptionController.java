package com.example.company.modules.contact.api.controller;


import com.example.company.modules.contact.app.dto.SubscriptionDTO;
import com.example.company.modules.contact.app.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscription")
@CrossOrigin(origins = "*") // allow frontend requests
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body("{\"error\": \"Email is required\"}");
        }

        SubscriptionDTO dto = subscriptionService.subscribe(email);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getAll() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    @GetMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestParam String email) {
        try {
            subscriptionService.unsubscribeByEmail(email);
            return ResponseEntity.ok("""    
            {"message": "You have been successfully unsubscribed from Meraki Systems updates."}
        """);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}


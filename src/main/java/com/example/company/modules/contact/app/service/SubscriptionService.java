package com.example.company.modules.contact.app.service;

import com.example.company.modules.contact.app.dto.SubscriptionDTO;
import java.util.List;

public interface SubscriptionService {
    SubscriptionDTO subscribe(String email);
    List<SubscriptionDTO> getAllSubscriptions();
    void deleteSubscription(Long id);
    void unsubscribeByEmail(String email);
}

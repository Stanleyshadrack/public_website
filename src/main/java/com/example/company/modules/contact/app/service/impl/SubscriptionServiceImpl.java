package com.example.company.modules.contact.app.service.impl;



import com.example.company.infra.mail.MerakiEmailService;
import com.example.company.modules.contact.app.dto.SubscriptionDTO;
import com.example.company.modules.contact.app.service.SubscriptionService;
import com.example.company.modules.contact.domain.entity.SubscriptionModel;
import com.example.company.modules.contact.domain.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final MerakiEmailService emailService;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
                                   MerakiEmailService emailService) {
        this.subscriptionRepository = subscriptionRepository;
        this.emailService = emailService;
    }

    @Override
    public SubscriptionDTO subscribe(String email) {
        if (subscriptionRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already subscribed");
        }

        SubscriptionModel model = new SubscriptionModel();
        model.setEmail(email);
        SubscriptionModel saved = subscriptionRepository.save(model);

        // Send the guide or welcome email
        emailService.sendSubscriptionGuideEmail(email);

        return new SubscriptionDTO(saved.getId(), saved.getEmail());
    }

    @Override
    public List<SubscriptionDTO> getAllSubscriptions() {
        return subscriptionRepository.findAll()
                .stream()
                .map(sub -> new SubscriptionDTO(sub.getId(), sub.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public void unsubscribeByEmail(String email) {
        subscriptionRepository.findByEmail(email).ifPresentOrElse(sub -> {
            subscriptionRepository.deleteById(sub.getId());
            emailService.sendUnsubscribeConfirmationEmail(email);
        }, () -> {
            throw new RuntimeException("Email not found or already unsubscribed");
        });
    }


    @Override
    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
}


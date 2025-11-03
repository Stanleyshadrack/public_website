package com.example.company.modules.faq.domain.repository;



import com.example.company.modules.faq.domain.entity.FaqModel;
import java.util.List;
import java.util.Optional;

public interface FaqRepository {
    List<FaqModel> getAllFaqs();
    Optional<FaqModel> findById(Long id);
    FaqModel saveFaq(FaqModel faq);
    void deleteFaq(FaqModel faq);
}

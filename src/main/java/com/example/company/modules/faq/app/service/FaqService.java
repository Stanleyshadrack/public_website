package com.example.company.modules.faq.app.service;



import com.example.company.modules.faq.app.dto.FaqDTO;
import java.util.List;

public interface FaqService {
    List<FaqDTO> getAllFaqs();
    FaqDTO getFaq(Long id);
    FaqDTO createFaq(FaqDTO dto);
    FaqDTO updateFaq(Long id, FaqDTO dto);
    void deleteFaq(Long id);
}

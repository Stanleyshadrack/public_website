package com.example.company.modules.faq.app.service.impl;

import com.example.company.modules.faq.app.dto.FaqDTO;
import com.example.company.modules.faq.app.service.FaqService;
import com.example.company.modules.faq.domain.entity.FaqModel;
import com.example.company.modules.faq.domain.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqRepository faqRepository; // ✅ depends on interface

    @Override
    public List<FaqDTO> getAllFaqs() {
        return faqRepository.getAllFaqs()
                .stream()
                .map(f -> new FaqDTO(
                        f.getId(),
                        f.getQuestion(),
                        f.getAnswer()
                )).toList();
    }

    @Override
    public FaqDTO getFaq(Long id) {
        FaqModel faq = faqRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ not found"));

        return new FaqDTO(faq.getId(), faq.getQuestion(), faq.getAnswer());
    }

    @Override
    public FaqDTO createFaq(FaqDTO dto) {
        FaqModel model = new FaqModel(dto.getQuestion(), dto.getAnswer());
        FaqModel saved = faqRepository.saveFaq(model);

        return new FaqDTO(saved.getId(), saved.getQuestion(), saved.getAnswer());
    }

    @Override
    public FaqDTO updateFaq(Long id, FaqDTO dto) {
        FaqModel faq = faqRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ not found"));

        if (dto.getQuestion() != null && !dto.getQuestion().isEmpty()) {
            faq.setQuestion(dto.getQuestion());
        }
        if (dto.getAnswer() != null && !dto.getAnswer().isEmpty()) {
            faq.setAnswer(dto.getAnswer());
        }

        FaqModel updated = faqRepository.saveFaq(faq);

        return new FaqDTO(updated.getId(), updated.getQuestion(), updated.getAnswer());
    }

    @Override
    public void deleteFaq(Long id) {
        FaqModel faq = faqRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ not found"));

        faqRepository.deleteFaq(faq);
    }
}

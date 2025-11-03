package com.example.company.modules.partner.domain.repository;



import com.example.company.modules.partner.domain.entity.PartnerModel;
import java.util.List;

public interface
PartnerRepository {
    List<PartnerModel> getAllPartners();
    PartnerModel savePartner(PartnerModel partner);
}

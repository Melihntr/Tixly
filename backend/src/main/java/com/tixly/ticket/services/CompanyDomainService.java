package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.AdminUser;
import com.tixly.ticket.entity.Company;
import com.tixly.ticket.models.dto.CompanyDTO;
import com.tixly.ticket.utils.BearerUtil;

@Service
public class CompanyDomainService {

 @Autowired
    private EntityService entityService;

    public CompanyDTO getCompany(String authKey){
        String jwtToken = BearerUtil.extractToken(authKey);
        AdminUser admin = entityService.getAdmin();
        Long userId=admin.getUserIdByAuthKey(jwtToken);
        Long companyId=admin.getCompanyIdByUserId(userId);
        Company company = entityService.getCompany();
        return company.getCompanyById(companyId);
    }

}

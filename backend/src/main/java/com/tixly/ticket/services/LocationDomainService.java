package com.tixly.ticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.Province;
import com.tixly.ticket.models.dto.ProvinceModel;
import com.tixly.ticket.services.EntityService;

@Service
public class LocationDomainService {

    private final EntityService entityService;

    @Autowired
    public LocationDomainService(EntityService entityService) {
        this.entityService = entityService;
    }

    public List<ProvinceModel> getAllProvinces() {
        Province provinceEntity = entityService.getProvince();
        return provinceEntity.getAllProvinces();
    }
}
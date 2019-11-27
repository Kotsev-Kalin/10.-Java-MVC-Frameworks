package com.residentevilsecurity.service;


import com.residentevilsecurity.domain.model.service.CapitalServiceModel;

import java.util.List;

public interface CapitalService {
    CapitalServiceModel save(CapitalServiceModel capitalServiceModel);

    CapitalServiceModel findById(String id);

    CapitalServiceModel findByName(String id);

    List<CapitalServiceModel> findAll();

    List<CapitalServiceModel> findAllOrderByName();
}

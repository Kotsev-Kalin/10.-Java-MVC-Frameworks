package softuni.residentevil.domain.service;

import softuni.residentevil.domain.model.service.CapitalServiceModel;

import java.util.List;

public interface CapitalService {
    CapitalServiceModel save(CapitalServiceModel capitalServiceModel);

    CapitalServiceModel findById(String id);

    CapitalServiceModel findByName(String id);

    List<CapitalServiceModel> findAll();

    List<CapitalServiceModel> findAllOrderByName();
}

package softuni.residentevil.domain.service;

import softuni.residentevil.domain.model.service.VirusServiceModel;

import java.util.List;

public interface VirusService {
    VirusServiceModel save(VirusServiceModel capitalServiceModel);

    VirusServiceModel update(VirusServiceModel virusServiceModel);

    VirusServiceModel findById(String id);

    List<VirusServiceModel> findAll();

    void deleteById(String id);
}

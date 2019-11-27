package softuni.exodia.service;

import softuni.exodia.domain.model.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService {
    DocumentServiceModel register(DocumentServiceModel documentServiceModel);

    List<DocumentServiceModel> findAll();

    DocumentServiceModel findById(String id);

    void deleteById(String id);
}

package com.productshop.service;

import com.productshop.domain.model.service.ProductServiceModel;

import java.util.List;

public interface ProductService {
    ProductServiceModel save(ProductServiceModel categoryServiceModel);

    ProductServiceModel findByName(String name);

    ProductServiceModel findById(String id);

    List<ProductServiceModel> findAll();

    void deleteById(String id);

    ProductServiceModel edit(ProductServiceModel productServiceModel);
}

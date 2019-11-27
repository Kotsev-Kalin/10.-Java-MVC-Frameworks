package com.productshop.service;

import com.productshop.domain.model.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {
    CategoryServiceModel save(CategoryServiceModel categoryServiceModel);

    CategoryServiceModel findByName(String name);

    CategoryServiceModel findById(String id);

    List<CategoryServiceModel> findAll();

    CategoryServiceModel edit(CategoryServiceModel model);

    void deleteById(String id);
}

package com.productshop.service;

import com.productshop.domain.entity.Category;
import com.productshop.domain.model.service.CategoryServiceModel;
import com.productshop.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryServiceModel save(CategoryServiceModel categoryServiceModel) {
        try {
            Category category = this.modelMapper.map(categoryServiceModel, Category.class);
            category = this.categoryRepository.save(category);
            return this.modelMapper.map(category, CategoryServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CategoryServiceModel findByName(String name) {
        Category category = this.categoryRepository.findByName(name).orElse(null);
        return category == null ? null : this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel findById(String id) {
        Category category = this.categoryRepository.findById(id).orElse(null);
        return category == null ? null : this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public List<CategoryServiceModel> findAll() {
        return this.categoryRepository.findAll()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel edit(CategoryServiceModel model) {
        try {
            this.categoryRepository.save(this.modelMapper.map(model, Category.class));
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteById(String id) {
        this.categoryRepository.deleteById(id);
    }
}

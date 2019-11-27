package com.productshop.service;

import com.productshop.domain.entity.Product;
import com.productshop.domain.model.service.ProductServiceModel;
import com.productshop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel save(ProductServiceModel categoryServiceModel) {
        try {
            Product product = this.productRepository
                    .save(this.modelMapper.map(categoryServiceModel, Product.class));
            return this.modelMapper.map(product, ProductServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ProductServiceModel findByName(String name) {
        Product product = this.productRepository.findByName(name).orElse(null);
        return product == null ? null : this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public ProductServiceModel findById(String id) {
        Product product = this.productRepository.findById(id).orElse(null);
        return product == null ? null : this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAll() {
        return this.productRepository.findAll()
                .stream()
                .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel edit(ProductServiceModel productServiceModel) {
        try {
            this.productRepository.save(this.modelMapper.map(productServiceModel, Product.class));
            return productServiceModel;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteById(String id) {
        this.productRepository.deleteById(id);
    }
}

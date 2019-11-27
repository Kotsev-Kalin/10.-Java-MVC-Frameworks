package com.productshop.domain.model.service;

import java.util.function.Predicate;

public class CategoryServiceModel {
    private String id;
    private String name;

    public CategoryServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

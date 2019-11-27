package com.productshop.domain.model.view;

import java.math.BigDecimal;
import java.util.List;

public class ProductViewModel {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private List<CategoryViewModel> categories;

    public ProductViewModel() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<CategoryViewModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryViewModel> categories) {
        this.categories = categories;
    }
}

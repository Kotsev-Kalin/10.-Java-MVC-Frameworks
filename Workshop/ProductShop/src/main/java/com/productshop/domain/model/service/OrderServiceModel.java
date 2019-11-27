package com.productshop.domain.model.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderServiceModel {
    private String id;
    private ProductServiceModel product;
    private UserServiceModel customer;
    private LocalDateTime orderDate;
    private long quantity;
    private BigDecimal totalPrice;

    public OrderServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductServiceModel getProduct() {
        return product;
    }

    public void setProduct(ProductServiceModel product) {
        this.product = product;
    }

    public UserServiceModel getCustomer() {
        return customer;
    }

    public void setCustomer(UserServiceModel customer) {
        this.customer = customer;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}

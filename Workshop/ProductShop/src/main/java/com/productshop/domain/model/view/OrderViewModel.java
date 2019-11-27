package com.productshop.domain.model.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderViewModel {
    private String id;
    private ProductViewModel product;
    private UserViewModel customer;
    private LocalDateTime orderDate;
    private long quantity;
    private BigDecimal totalPrice;

    public OrderViewModel() {
    }

    public ProductViewModel getProduct() {
        return product;
    }

    public void setProduct(ProductViewModel product) {
        this.product = product;
    }

    public UserViewModel getCustomer() {
        return customer;
    }

    public void setCustomer(UserViewModel customer) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

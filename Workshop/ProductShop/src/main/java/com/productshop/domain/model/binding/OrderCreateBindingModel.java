package com.productshop.domain.model.binding;

public class OrderCreateBindingModel {
    private long quantity;

    public OrderCreateBindingModel() {
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}

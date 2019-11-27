package com.productshop.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private Product product;
    private User customer;
    private LocalDateTime orderDate;
    private long quantity;
    private BigDecimal totalPrice;

    public Order() {
    }

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    @Column(name = "order_date")
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Column(name = "quantity")
    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "total_price", scale = 2, precision = 8)
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}

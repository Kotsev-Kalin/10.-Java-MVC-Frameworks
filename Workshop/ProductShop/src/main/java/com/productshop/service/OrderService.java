package com.productshop.service;

import com.productshop.domain.model.service.OrderServiceModel;

import java.util.List;

public interface OrderService {
    OrderServiceModel save(OrderServiceModel orderServiceModel);

    OrderServiceModel findById(String id);

    List<OrderServiceModel> findAll();

    boolean createOrder(String productId, String userId, long quantity);
}

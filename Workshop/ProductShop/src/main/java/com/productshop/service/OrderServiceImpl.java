package com.productshop.service;

import com.productshop.domain.entity.Order;
import com.productshop.domain.model.service.OrderServiceModel;
import com.productshop.domain.model.service.ProductServiceModel;
import com.productshop.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ProductService productService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderServiceModel save(OrderServiceModel orderServiceModel) {
        try {
            Order order = this.orderRepository.save(this.modelMapper.map(orderServiceModel, Order.class));
            return this.modelMapper.map(order, OrderServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public OrderServiceModel findById(String id) {
        Order model = this.orderRepository.findById(id).orElse(null);
        return model == null ? null : this.modelMapper.map(model, OrderServiceModel.class);
    }

    @Override
    public List<OrderServiceModel> findAll() {
        return this.orderRepository.findAll()
                .stream()
                .map(order -> this.modelMapper.map(order, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean createOrder(String productId, String userId, long quantity) {
        OrderServiceModel order = new OrderServiceModel();
        order.setCustomer(this.userService.findById(userId));
        ProductServiceModel productServiceModel = this.productService.findById(productId);
        order.setProduct(productServiceModel);
        order.setOrderDate(LocalDateTime.now());
        order.setQuantity(quantity);
        order.setTotalPrice(productServiceModel.getPrice().multiply(new BigDecimal(quantity)));
        return this.save(order) == null;
    }
}

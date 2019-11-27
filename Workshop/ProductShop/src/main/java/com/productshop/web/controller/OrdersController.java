package com.productshop.web.controller;

import com.productshop.domain.entity.User;
import com.productshop.domain.model.binding.OrderCreateBindingModel;
import com.productshop.domain.model.view.OrderViewModel;
import com.productshop.domain.model.view.ProductViewModel;
import com.productshop.service.OrderService;
import com.productshop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    private final OrderService orderService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrdersController(OrderService orderService, ProductService productService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/order-product/{id}")
    public ModelAndView orderProduct(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.addObject("product",
                this.modelMapper.map(this.productService.findById(id), ProductViewModel.class));
        modelAndView.setViewName("order");
        return modelAndView;
    }

    @PostMapping("/order-product/{id}")
    public ModelAndView confirmOrderProduct(@ModelAttribute(name = "product") OrderCreateBindingModel order,
                                            @PathVariable("id") String id, Authentication authentication,
                                            ModelAndView modelAndView) {
        this.orderService.createOrder(id, ((User) authentication.getPrincipal()).getId(), order.getQuantity());
        modelAndView.setViewName("redirect:/orders/details/" + id);
        return modelAndView;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    public ModelAndView all(ModelAndView modelAndView) {
        modelAndView.addObject("orders", this.orderService
                .findAll()
                .stream()
                .map(orderServiceModel -> this.modelMapper.map(orderServiceModel, OrderViewModel.class))
                .collect(Collectors.toList()));
        modelAndView.setViewName("all-orders");
        return modelAndView;
    }

    @GetMapping("/my")
    public ModelAndView my(ModelAndView modelAndView, Authentication authentication) {
        String id = ((User) authentication.getPrincipal()).getId();
        modelAndView.addObject("orders", this.orderService
                .findAll()
                .stream()
                .filter(o -> o.getCustomer().getId().equals(id))
                .map(orderServiceModel -> this.modelMapper.map(orderServiceModel, OrderViewModel.class))
                .collect(Collectors.toList()));
        modelAndView.setViewName("my-orders");
        return modelAndView;
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.addObject("order", this.modelMapper.map(this.orderService.findById(id), OrderViewModel.class));
        modelAndView.setViewName("order-details");
        return modelAndView;
    }
}

package com.productshop.web.controller;

import com.productshop.domain.model.binding.ProductCreateBindingModel;
import com.productshop.domain.model.service.ProductServiceModel;
import com.productshop.domain.model.view.CategoryViewModel;
import com.productshop.domain.model.view.ProductViewModel;
import com.productshop.service.CategoryService;
import com.productshop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute(name = "product") ProductCreateBindingModel product,
                            ModelAndView modelAndView) {
        modelAndView.setViewName("add-product");
        modelAndView.addObject("categories", this.categoryService.findAll()
                .stream()
                .map(cat -> this.modelMapper.map(cat, CategoryViewModel.class))
                .collect(Collectors.toList()));
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView confirmAdd(@RequestParam("file") MultipartFile file,
                                   @ModelAttribute(name = "product") ProductCreateBindingModel product,
                                   ModelAndView modelAndView) throws IOException {
        ProductServiceModel registeredModel = this.productService.save(this.modelMapper.map(product, ProductServiceModel.class));
        if (registeredModel != null) {
            String filePath = "D:\\Програмиране\\СофтУни\\Java Web\\SoftUni_Java_Web\\Java MVC Frameworks\\Workshop\\ProductShop\\src\\main\\resources\\static\\image";
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            File f1 = new File(filePath + "\\" + registeredModel.getId() + extension);
            file.transferTo(f1);
            modelAndView.setViewName("redirect:/products/all");
        } else {
            modelAndView.setViewName("redirect:/products/add");
        }
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView all(ModelAndView modelAndView) {
        modelAndView.setViewName("all-products");
        modelAndView.addObject("products", this.productService.findAll()
                .stream()
                .map(product -> this.modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList()));
        return modelAndView;
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.setViewName("details");
        modelAndView.addObject("product",
                this.modelMapper.map(this.productService.findById(id), ProductViewModel.class));
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id, ModelAndView modelAndView) {
        ProductViewModel model =  this.modelMapper.map(this.productService.findById(id), ProductViewModel.class);
        modelAndView.setViewName("edit-product");
        modelAndView.addObject("product", model);
        modelAndView.addObject("categories", this.categoryService.findAll()
                .stream()
                .map(cat -> this.modelMapper.map(cat, CategoryViewModel.class))
                .collect(Collectors.toList()));
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView confirmEdit(@PathVariable("id") String id,
                                    @ModelAttribute(name = "product") ProductCreateBindingModel product,
                                    ModelAndView modelAndView) {
        ProductServiceModel model = this.modelMapper.map(product, ProductServiceModel.class);
        model.setId(id);
        if (this.productService.edit(model) != null) {
            modelAndView.setViewName("redirect:/products/all");
        } else {
            modelAndView.setViewName("redirect:/products/edit/" + id);
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.setViewName("delete-product");
        modelAndView.addObject("product",
                this.modelMapper.map(this.productService.findById(id), ProductViewModel.class));
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView confirmDelete(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.productService.deleteById(id);
        modelAndView.setViewName("redirect:/products/all");
        return modelAndView;
    }
}

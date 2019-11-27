package com.productshop.web.controller;

import com.productshop.domain.model.binding.CategoryCreateBindingModel;
import com.productshop.domain.model.service.CategoryServiceModel;
import com.productshop.domain.model.view.CategoryViewModel;
import com.productshop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute(name = "category") CategoryCreateBindingModel category,
                            ModelAndView modelAndView) {
        modelAndView.setViewName("add-category");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView confirmAdd(@ModelAttribute(name = "category") CategoryCreateBindingModel category,
                                   ModelAndView modelAndView) {
        if (this.categoryService.save(this.modelMapper.map(category, CategoryServiceModel.class)) != null) {
            modelAndView.setViewName("redirect:/categories/all");
        } else {
            modelAndView.setViewName("redirect:/categories/add");
        }
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView all(ModelAndView modelAndView) {
        modelAndView.setViewName("all-categories");
        modelAndView.addObject("categories", this.categoryService.findAll()
                .stream()
                .map(cat -> this.modelMapper.map(cat, CategoryViewModel.class))
                .collect(Collectors.toList()));
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.setViewName("edit-category");
        modelAndView.addObject("category",
                this.modelMapper.map(this.categoryService.findById(id), CategoryViewModel.class));
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView confirmEdit(@PathVariable("id") String id,
                                    @ModelAttribute(name = "category") CategoryCreateBindingModel category,
                                    ModelAndView modelAndView) {
        CategoryServiceModel model = this.modelMapper.map(category, CategoryServiceModel.class);
        model.setId(id);
        if (this.categoryService.edit(model) != null) {
            modelAndView.setViewName("redirect:/categories/all");
        } else {
            modelAndView.setViewName("redirect:/categories/edit/" + id);
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.setViewName("delete-category");
        modelAndView.addObject("category",
                this.modelMapper.map(this.categoryService.findById(id), CategoryViewModel.class));
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView confirmDelete(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.categoryService.deleteById(id);
        modelAndView.setViewName("redirect:/categories/all");
        return modelAndView;
    }
}

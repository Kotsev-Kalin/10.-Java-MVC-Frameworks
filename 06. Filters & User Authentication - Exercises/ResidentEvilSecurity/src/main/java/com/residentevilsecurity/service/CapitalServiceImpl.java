package com.residentevilsecurity.service;

import com.residentevilsecurity.domain.entity.Capital;
import com.residentevilsecurity.domain.model.service.CapitalServiceModel;
import com.residentevilsecurity.repository.CapitalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CapitalServiceImpl implements CapitalService {
    private final CapitalRepository capitalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CapitalServiceImpl(CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CapitalServiceModel save(CapitalServiceModel capitalServiceModel) {
        try {
            this.capitalRepository.saveAndFlush(this.modelMapper.map(capitalServiceModel, Capital.class));
            return capitalServiceModel;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CapitalServiceModel findById(String id) {
        Capital capital = this.capitalRepository.findById(id).orElse(null);
        return capital == null ? null : this.modelMapper.map(capital, CapitalServiceModel.class);
    }

    @Override
    public CapitalServiceModel findByName(String name) {
        Capital capital = this.capitalRepository.findByName(name);
        return capital == null ? null : this.modelMapper.map(capital, CapitalServiceModel.class);
    }

    @Override
    public List<CapitalServiceModel> findAll() {
        return this.capitalRepository.findAll()
                .stream()
                .map(capital -> this.modelMapper.map(capital, CapitalServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CapitalServiceModel> findAllOrderByName(){
        return this.capitalRepository.findAllByOrderByName()
                .stream()
                .map(capital -> this.modelMapper.map(capital, CapitalServiceModel.class))
                .collect(Collectors.toList());
    }
}

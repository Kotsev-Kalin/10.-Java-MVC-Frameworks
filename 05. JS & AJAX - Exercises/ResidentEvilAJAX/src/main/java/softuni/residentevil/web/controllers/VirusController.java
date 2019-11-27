package softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.domain.enums.Magnitude;
import softuni.residentevil.domain.enums.Mutation;
import softuni.residentevil.domain.model.binding.VirusRegisterBindingModel;
import softuni.residentevil.domain.model.service.VirusServiceModel;
import softuni.residentevil.domain.model.view.CapitalViewModel;
import softuni.residentevil.domain.model.view.VirusViewModel;
import softuni.residentevil.domain.service.CapitalService;
import softuni.residentevil.domain.service.VirusService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class VirusController {
    private final VirusService virusService;
    private final CapitalService capitalService;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusController(VirusService virusService, CapitalService capitalService, ModelMapper modelMapper) {
        this.virusService = virusService;
        this.capitalService = capitalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/viruses/show")
    public ModelAndView show(ModelAndView modelAndView) {
        modelAndView.setViewName("show");
        modelAndView.addObject("viruses", virusService.findAll()
                .stream()
                .map(virus -> this.modelMapper.map(virus, VirusViewModel.class))
                .collect(Collectors.toList()));
        modelAndView.addObject("capitals", capitalService.findAll()
                .stream()
                .map(capital -> this.modelMapper.map(capital, CapitalViewModel.class))
                .collect(Collectors.toList()));
        return modelAndView;
    }

    @GetMapping(value = "/fetch/capitals", produces = "application/json")
    @ResponseBody
    public Object fetchCapitals() {
        return capitalService.findAll()
                .stream()
                .map(capital -> this.modelMapper.map(capital, CapitalViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/fetch/viruses", produces = "application/json")
    @ResponseBody
    public Object fetchViruses() {
        return virusService.findAll()
                .stream()
                .map(virus -> this.modelMapper.map(virus, VirusViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/viruses/add")
    public ModelAndView add(@ModelAttribute(name = "virus") VirusRegisterBindingModel virus,
                            ModelAndView modelAndView) {
        initData(modelAndView, "add");
        return modelAndView;
    }

    @PostMapping("/viruses/add")
    public ModelAndView confirmAdd(@Valid @ModelAttribute(name = "virus") VirusRegisterBindingModel virus,
                                   BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            initData(modelAndView, "add");
        } else {
            VirusServiceModel virusServiceModel = this.modelMapper.map(virus, VirusServiceModel.class);
            virusServiceModel.setCapitals(this.capitalService.findAll()
                    .stream()
                    .filter(capitalServiceModel -> virus.getCapitals().contains(capitalServiceModel.getId()))
                    .collect(Collectors.toList()));
            this.virusService.save(virusServiceModel);
            modelAndView.setViewName("redirect:/viruses/show");
        }
        return modelAndView;
    }

    @GetMapping("/viruses/edit/{id}")
    public ModelAndView edit(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        VirusServiceModel virusServiceModel = this.virusService.findById(id);
        if (virusServiceModel == null) {
            throw new IllegalArgumentException("Virus not found");
        }
        VirusViewModel virus = this.modelMapper.map(virusServiceModel, VirusViewModel.class);
        initData(modelAndView, "edit");
        modelAndView.addObject("virus", virus);
        return modelAndView;
    }

    @PostMapping("/viruses/edit/{id}")
    public ModelAndView confirmEdit(@PathVariable(name = "id") String id,
                                    @Valid @ModelAttribute(name = "virus") VirusRegisterBindingModel virus,
                                    BindingResult bindingResult, ModelAndView modelAndView) {
        VirusServiceModel virusServiceModel = this.modelMapper.map(virus, VirusServiceModel.class);
        virusServiceModel.setId(id);
        if (bindingResult.hasErrors()) {
            initData(modelAndView, "edit");
            modelAndView.addObject("virus", this.modelMapper.map(virusServiceModel, VirusViewModel.class));
        } else {
            virusServiceModel.setCapitals(this.capitalService.findAll()
                    .stream()
                    .filter(capitalServiceModel -> virus.getCapitals().contains(capitalServiceModel.getId()))
                    .collect(Collectors.toList()));
            this.virusService.update(virusServiceModel);
            modelAndView.setViewName("redirect:/viruses/show");
        }
        return modelAndView;
    }

    @GetMapping("/viruses/delete/{id}")
    public ModelAndView delete(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        this.virusService.deleteById(id);
        modelAndView.setViewName("redirect:/viruses/show");
        return modelAndView;
    }

    private void initData(ModelAndView modelAndView, String viewName) {
        modelAndView.setViewName(viewName);
        modelAndView.addObject("magnitudes", Magnitude.values());
        modelAndView.addObject("mutations", Mutation.values());
        modelAndView.addObject("capitals", capitalService.findAllOrderByName());
    }
}

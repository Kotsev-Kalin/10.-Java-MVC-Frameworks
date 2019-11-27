package softuni.residentevil.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.residentevil.domain.entity.Capital;
import softuni.residentevil.domain.entity.Virus;
import softuni.residentevil.domain.model.service.VirusServiceModel;
import softuni.residentevil.repository.VirusRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirusServiceImpl implements VirusService {
    private final VirusRepository virusRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VirusServiceModel save(VirusServiceModel capitalServiceModel) {
        try {
            this.virusRepository.save(this.modelMapper.map(capitalServiceModel, Virus.class));
            return capitalServiceModel;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VirusServiceModel update(VirusServiceModel virusServiceModel) {
        Virus virus = this.virusRepository.findById(virusServiceModel.getId()).orElse(null);
        if (virus == null) {
            throw new IllegalArgumentException("virus not found");
        }
        virus.setName(virusServiceModel.getName());
        virus.setDescription(virusServiceModel.getDescription());
        virus.setSideEffects(virusServiceModel.getSideEffects());
        virus.setCreator(virusServiceModel.getCreator());
        virus.setDeadly(virusServiceModel.isDeadly());
        virus.setCurable(virusServiceModel.isCurable());
        virus.setMutation(virusServiceModel.getMutation());
        virus.setTurnoverRate(virusServiceModel.getTurnoverRate());
        virus.setHoursUntilTurn(virusServiceModel.getHoursUntilTurn());
        virus.setMagnitude(virusServiceModel.getMagnitude());
        virus.setReleasedOn(virusServiceModel.getReleasedOn());
        virus.setCapitals(virusServiceModel.getCapitals().stream()
                .map(capitalServiceModel -> this.modelMapper.map(capitalServiceModel, Capital.class))
                .collect(Collectors.toList()));
        virus = this.virusRepository.saveAndFlush(virus);
        return this.modelMapper.map(virus, VirusServiceModel.class);
    }

    @Override
    public VirusServiceModel findById(String id) {
        Virus capital = this.virusRepository.findById(id).orElse(null);
        return capital == null ? null : this.modelMapper.map(capital, VirusServiceModel.class);
    }

    @Override
    public List<VirusServiceModel> findAll() {
        return this.virusRepository.findAll()
                .stream()
                .map(capital -> this.modelMapper.map(capital, VirusServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        this.virusRepository.deleteById(id);
    }
}

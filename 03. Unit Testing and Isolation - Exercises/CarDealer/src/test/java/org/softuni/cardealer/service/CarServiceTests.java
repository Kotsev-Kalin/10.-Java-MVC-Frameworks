package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.softuni.cardealer.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceTests {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private PartRepository partRepository;
    private ModelMapper modelMapper;
    private CarService carService;
    private List<PartServiceModel> parts;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.carService = new CarServiceImpl(this.carRepository, this.modelMapper);
        PartServiceModel first = new PartServiceModel() {{
            setName("newPart");
            setPrice(new BigDecimal("25.50"));
        }};
        PartServiceModel second = new PartServiceModel() {{
            setName("newPart2");
            setPrice(new BigDecimal("30.00"));
        }};
        this.partRepository.saveAndFlush(this.modelMapper.map(first, Part.class));
        this.partRepository.saveAndFlush(this.modelMapper.map(second, Part.class));
        this.parts = partRepository.findAll().stream()
                .map(part -> this.modelMapper.map(part, PartServiceModel.class))
                .collect(Collectors.toList());
    }

    @Test
    public void carService_saveCarWithCorrectParams_returnsServiceModel() {
        CarServiceModel carServiceModel = new CarServiceModel() {{
            setMake("ford");
            setModel("fiesta");
            setTravelledDistance(200000L);
            setParts(parts);
        }};
        CarServiceModel expected = this.carService.saveCar(carServiceModel);
        CarServiceModel actual = this.modelMapper
                .map(this.carRepository.findAll().get(0), CarServiceModel.class);
        Assert.assertEquals("Save method does not function properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Save method does not function properly.", expected.getMake(), actual.getMake());
        Assert.assertEquals("Save method does not function properly.", expected.getModel(), actual.getModel());
        Assert.assertEquals("Save method does not function properly.", expected.getTravelledDistance(), actual.getTravelledDistance());
        Assert.assertEquals("Save method does not function properly.", expected.getParts().get(0).getId(), actual.getParts().get(0).getId());
    }

    @Test(expected = Exception.class)
    public void carService_saveCarWithNoParams_throwsException() {
        CarServiceModel carServiceModel = this.carService.saveCar(new CarServiceModel());
    }

    @Test
    public void carService_editCarWithCorrectParams_returnsServiceModel() {
        CarServiceModel carServiceModel = new CarServiceModel() {{
            setMake("ford");
            setModel("fiesta");
            setTravelledDistance(200000L);
            setParts(parts);
        }};
        this.carRepository.saveAndFlush(this.modelMapper.map(carServiceModel, Car.class));
        CarServiceModel edited = new CarServiceModel() {{
            setId(carRepository.findAll().get(0).getId());
            setMake("fiat");
            setModel("punto");
            setTravelledDistance(305000L);
            setParts(parts);
        }};
        CarServiceModel expected = this.carService.editCar(edited);
        CarServiceModel actual = this.modelMapper
                .map(this.carRepository.findAll().get(0), CarServiceModel.class);
        Assert.assertEquals("Edit method does not function properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Edit method does not function properly.", expected.getMake(), actual.getMake());
        Assert.assertEquals("Edit method does not function properly.", expected.getModel(), actual.getModel());
        Assert.assertEquals("Edit method does not function properly.", expected.getTravelledDistance(), actual.getTravelledDistance());
        Assert.assertEquals("Edit method does not function properly.", expected.getParts().get(0).getId(), actual.getParts().get(0).getId());
    }

    @Test(expected = Exception.class)
    public void carService_editCarWithNoParams_throwsException() {
        CarServiceModel carServiceModel = this.carService.editCar(new CarServiceModel());
    }

    @Test
    public void carService_deleteCarWithCorrectId_returnsServiceModel() {
        CarServiceModel carServiceModel = new CarServiceModel() {{
            setMake("ford");
            setModel("fiesta");
            setTravelledDistance(200000L);
            setParts(parts);
        }};
        this.carRepository.saveAndFlush(this.modelMapper.map(carServiceModel, Car.class));
        String id = carRepository.findAll().get(0).getId();
        CarServiceModel actual = this.modelMapper
                .map(this.carRepository.findAll().get(0), CarServiceModel.class);
        CarServiceModel expected = this.carService.deleteCar(id);
        Assert.assertEquals("Delete method does not function properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Delete method does not function properly.", expected.getMake(), actual.getMake());
        Assert.assertEquals("Delete method does not function properly.", expected.getModel(), actual.getModel());
        Assert.assertEquals("Delete method does not function properly.", expected.getTravelledDistance(), actual.getTravelledDistance());
        Assert.assertEquals("Delete method does not function properly.", expected.getParts().get(0).getId(), actual.getParts().get(0).getId());
    }

    @Test(expected = Exception.class)
    public void carService_saveCarWithIdNull_throwsException() {
        CarServiceModel carServiceModel = this.carService.deleteCar(null);
    }

    @Test
    public void carService_findCarByIdWithCorrectId_returnsServiceModel() {
        CarServiceModel carServiceModel = new CarServiceModel() {{
            setMake("ford");
            setModel("fiesta");
            setTravelledDistance(200000L);
            setParts(parts);
        }};
        this.carRepository.saveAndFlush(this.modelMapper.map(carServiceModel, Car.class));
        String id = carRepository.findAll().get(0).getId();
        CarServiceModel actual = this.modelMapper
                .map(this.carRepository.findAll().get(0), CarServiceModel.class);
        CarServiceModel expected = this.carService.findCarById(id);
        Assert.assertEquals("Find by Id method does not function properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Find by Id method does not function properly.", expected.getMake(), actual.getMake());
        Assert.assertEquals("Find by Id method does not function properly.", expected.getModel(), actual.getModel());
        Assert.assertEquals("Find by Id method does not function properly.", expected.getTravelledDistance(), actual.getTravelledDistance());
        Assert.assertEquals("Find by Id method does not function properly.", expected.getParts().get(0).getId(), actual.getParts().get(0).getId());
    }

    @Test(expected = Exception.class)
    public void carService_findCarByIdWithIdNull_throwsException() {
        CarServiceModel carServiceModel = this.carService.findCarById(null);
    }

}

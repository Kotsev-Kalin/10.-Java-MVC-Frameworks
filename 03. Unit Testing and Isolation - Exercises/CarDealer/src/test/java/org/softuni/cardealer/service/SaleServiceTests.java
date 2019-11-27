package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.models.service.*;
import org.softuni.cardealer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SaleServiceTests {
    @Autowired
    private CarSaleRepository carSaleRepository;
    @Autowired
    private PartSaleRepository partSaleRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    private ModelMapper modelMapper;
    private SaleService saleService;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.saleService = new SaleServiceImpl(this.carSaleRepository, this.partSaleRepository, this.modelMapper);
        CarServiceModel car = new CarServiceModel() {{
            setMake("ford");
            setModel("fiesta");
            setTravelledDistance(20000L);
        }};
        CustomerServiceModel customer = new CustomerServiceModel() {{
            setName("Pesho");
            setBirthDate(LocalDate.now());
            setYoungDriver(true);
        }};
        PartServiceModel part = new PartServiceModel() {{
            setName("asd");
            setPrice(new BigDecimal("1.25"));
        }};
        partRepository.saveAndFlush(this.modelMapper.map(part, Part.class));
        carRepository.saveAndFlush(this.modelMapper.map(car, Car.class));
        customerRepository.saveAndFlush(this.modelMapper.map(customer, Customer.class));
    }

    @Test
    public void saleService_saleCarWithCorrectParams_returnsServiceModel() {
        CarSaleServiceModel model = new CarSaleServiceModel() {{
            setCar(modelMapper.map(carRepository.findAll().get(0), CarServiceModel.class));
            setCustomer(modelMapper.map(customerRepository.findAll().get(0), CustomerServiceModel.class));
            setDiscount(1.25);
        }};
        CarSaleServiceModel actual = this.saleService.saleCar(model);
        CarSaleServiceModel expected = this.modelMapper
                .map(this.carSaleRepository.findAll().get(0), CarSaleServiceModel.class);
        Assert.assertEquals("Sale Car method does not function properly", actual.getCar().getId(), expected.getCar().getId());
        Assert.assertEquals("Sale Car method does not function properly", actual.getId(), expected.getId());
        Assert.assertEquals("Sale Car method does not function properly", actual.getCustomer().getId(), expected.getCustomer().getId());
        Assert.assertEquals("Sale Car method does not function properly", actual.getDiscount(), expected.getDiscount());
    }

    @Test(expected = Exception.class)
    public void saleService_saleCarWithNull_throwsException() {
        CarSaleServiceModel carSaleServiceModel = this.saleService.saleCar(null);
    }

    @Test
    public void saleService_salePartWithCorrectParams_returnsServiceModel() {
        PartSaleServiceModel model = new PartSaleServiceModel() {{
            setPart(modelMapper.map(partRepository.findAll().get(0), PartServiceModel.class));
            setCustomer(modelMapper.map(customerRepository.findAll().get(0), CustomerServiceModel.class));
            setQuantity(1000);
            setDiscount(250.56);
        }};
        PartSaleServiceModel actual = this.saleService.salePart(model);
        PartSaleServiceModel expected = this.modelMapper
                .map(this.partSaleRepository.findAll().get(0), PartSaleServiceModel.class);
        Assert.assertEquals("Sale Part method does not function properly", actual.getPart().getName(), expected.getPart().getName());
        Assert.assertEquals("Sale Part method does not function properly", actual.getId(), expected.getId());
        Assert.assertEquals("Sale Part method does not function properly", actual.getCustomer().getName(), expected.getCustomer().getName());
        Assert.assertEquals("Sale Part method does not function properly", actual.getDiscount(), expected.getDiscount());
    }

    @Test(expected = Exception.class)
    public void saleService_salePartWithNull_throwsException() {
        PartSaleServiceModel partSaleServiceModel = this.saleService.salePart(null);
    }
}

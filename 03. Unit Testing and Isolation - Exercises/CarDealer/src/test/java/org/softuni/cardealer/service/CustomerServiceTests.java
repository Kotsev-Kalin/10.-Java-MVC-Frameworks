package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerServiceTests {
    @Autowired
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;
    private CustomerService customerService;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.customerService = new CustomerServiceImpl(this.customerRepository, this.modelMapper);
    }

    @Test
    public void customerService_saveCustomerWithCorrectParameters_returnsServiceModel() {
        CustomerServiceModel model = new CustomerServiceModel() {{
            setName("pesho");
            setBirthDate(LocalDate.now());
            setYoungDriver(true);
        }};
        CustomerServiceModel actual = this.customerService.saveCustomer(model);
        CustomerServiceModel expected = this.modelMapper
                .map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        Assert.assertEquals("Save method does not work properly.", actual.getId(), expected.getId());
        Assert.assertEquals("Save method does not work properly.", actual.getName(), expected.getName());
        Assert.assertEquals("Save method does not work properly.", actual.getBirthDate(), expected.getBirthDate());
        Assert.assertEquals("Save method does not work properly.", actual.isYoungDriver(), expected.isYoungDriver());
    }

    @Test(expected = Exception.class)
    public void customerService_saveCustomerWithNoParameters_throwsException() {
        CustomerServiceModel customerServiceModel = this.customerService.saveCustomer(new CustomerServiceModel());
    }

    @Test
    public void customerService_editCustomerWithCorrectParameters_returnsServiceModel() {
        CustomerServiceModel model = new CustomerServiceModel() {{
            setName("pesho");
            setBirthDate(LocalDate.now());
            setYoungDriver(true);
        }};
        this.customerRepository.saveAndFlush(this.modelMapper.map(model, Customer.class));
        CustomerServiceModel edited = new CustomerServiceModel() {{
            setId(customerRepository.findAll().get(0).getId());
            setName("gosho");
            setBirthDate(LocalDate.now().minusDays(1));
            setYoungDriver(false);
        }};
        CustomerServiceModel actual = this.customerService.editCustomer(edited);
        CustomerServiceModel expected = this.modelMapper
                .map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        Assert.assertEquals("Edit method does not work properly.", actual.getId(), expected.getId());
        Assert.assertEquals("Edit method does not work properly.", actual.getName(), expected.getName());
        Assert.assertEquals("Edit method does not work properly.", actual.getBirthDate(), expected.getBirthDate());
        Assert.assertEquals("Edit method does not work properly.", actual.isYoungDriver(), expected.isYoungDriver());
    }

    @Test(expected = Exception.class)
    public void customerService_editCustomerWithNull_throwsException() {
        CustomerServiceModel customerServiceModel = this.customerService.editCustomer(null);
    }

    @Test
    public void customerService_deleteCustomerWithCorrectId_returnsServiceModel() {
        CustomerServiceModel model = new CustomerServiceModel() {{
            setName("pesho");
            setBirthDate(LocalDate.now());
            setYoungDriver(true);
        }};
        this.customerRepository.saveAndFlush(this.modelMapper.map(model, Customer.class));
        String id = this.customerRepository.findAll().get(0).getId();
        CustomerServiceModel expected = this.modelMapper
                .map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        CustomerServiceModel actual = this.customerService.deleteCustomer(id);
        Assert.assertEquals("Delete method does not work properly.", actual.getId(), expected.getId());
        Assert.assertEquals("Delete method does not work properly.", actual.getName(), expected.getName());
        Assert.assertEquals("Delete method does not work properly.", actual.getBirthDate(), expected.getBirthDate());
        Assert.assertEquals("Delete method does not work properly.", actual.isYoungDriver(), expected.isYoungDriver());
    }

    @Test(expected = Exception.class)
    public void customerService_saveCustomerWithNull_throwsException() {
        CustomerServiceModel customerServiceModel = this.customerService.deleteCustomer(null);
    }

    @Test
    public void customerService_findCustomerByIdWithCorrectId_returnsServiceModel() {
        CustomerServiceModel model = new CustomerServiceModel() {{
            setName("pesho");
            setBirthDate(LocalDate.now());
            setYoungDriver(true);
        }};
        this.customerRepository.saveAndFlush(this.modelMapper.map(model, Customer.class));
        String id = this.customerRepository.findAll().get(0).getId();
        CustomerServiceModel expected = this.modelMapper
                .map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        CustomerServiceModel actual = this.customerService.findCustomerById(id);
        Assert.assertEquals("Find by Id method does not work properly.", actual.getId(), expected.getId());
        Assert.assertEquals("Find by Id method does not work properly.", actual.getName(), expected.getName());
        Assert.assertEquals("Find by Id method does not work properly.", actual.getBirthDate(), expected.getBirthDate());
        Assert.assertEquals("Find by Id method does not work properly.", actual.isYoungDriver(), expected.isYoungDriver());
    }

    @Test(expected = Exception.class)
    public void customerService_findCustomerByIdWithIdNull_throwsException() {
        CustomerServiceModel customerById = this.customerService.findCustomerById(null);
    }

}

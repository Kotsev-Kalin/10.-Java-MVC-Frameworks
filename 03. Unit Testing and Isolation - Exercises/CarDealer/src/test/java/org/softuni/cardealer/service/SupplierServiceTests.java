package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplierServiceTests {
    @Autowired
    private SupplierRepository supplierRepository;
    private ModelMapper modelMapper;
    private SupplierService supplierService;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.supplierService = new SupplierServiceImpl(this.supplierRepository, this.modelMapper);
    }

    @Test
    public void supplierService_saveSupplier_returnsCorrectModel() {
        SupplierServiceModel model = new SupplierServiceModel() {{
            setName("pesho");
            setImporter(true);
        }};
        SupplierServiceModel actual = this.supplierService.saveSupplier(model);
        SupplierServiceModel expected =
                this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        Assert.assertEquals("Save method does not work properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Save method does not work properly.", expected.getName(), actual.getName());
        Assert.assertEquals("Save method does not work properly.", expected.isImporter(), actual.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierService_saveSupplierWithoutParameters_throwsException(){
        this.supplierService.saveSupplier(new SupplierServiceModel());
    }

    @Test
    public void supplierService_editSupplier_returnsEditedModel() {
        SupplierServiceModel toBeSaved = new SupplierServiceModel() {{
            setName("Gosho");
            setImporter(true);
        }};
        this.supplierRepository.saveAndFlush(this.modelMapper.map(toBeSaved, Supplier.class));
        SupplierServiceModel model = new SupplierServiceModel() {{
            setId(supplierRepository.findAll().get(0).getId());
            setName("pesho");
            setImporter(false);
        }};
        SupplierServiceModel actual = this.supplierService.editSupplier(model);
        SupplierServiceModel expected = this.modelMapper
                .map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        Assert.assertEquals("Edit method does not work properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Edit method does not work properly.", expected.getName(), actual.getName());
        Assert.assertEquals("Edit method does not work properly.", expected.isImporter(), actual.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierService_editSupplierWithoutId_throwsException() {
        this.supplierService.editSupplier(new SupplierServiceModel());
    }

    @Test
    public void supplierService_deleteSupplier_deletesEntity() {
        SupplierServiceModel toBeSaved = new SupplierServiceModel() {{
            setName("Pesho");
            setImporter(true);
        }};
        this.supplierRepository.saveAndFlush(this.modelMapper.map(toBeSaved, Supplier.class));
        SupplierServiceModel expected = this.modelMapper
                .map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        SupplierServiceModel actual = this.supplierService
                .deleteSupplier(this.supplierRepository.findAll().get(0).getId());
        Assert.assertEquals("Delete method does not work properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Delete method does not work properly.", expected.getName(), actual.getName());
        Assert.assertEquals("Delete method does not work properly.", expected.isImporter(), actual.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierService_deleteSupplierWithoutId_throwsException() {
        this.supplierService.editSupplier(null);
    }

    @Test
    public void supplierService_findSupplierById_returnsSupplier() {
        SupplierServiceModel toBeSaved = new SupplierServiceModel() {{
            setName("Pesho");
            setImporter(true);
        }};
        this.supplierRepository.saveAndFlush(this.modelMapper.map(toBeSaved, Supplier.class));
        SupplierServiceModel expected = this.modelMapper
                .map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        SupplierServiceModel actual = this.supplierService
                .findSupplierById(this.supplierRepository.findAll().get(0).getId());
        Assert.assertEquals("Find by Id method does not work properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Find by Id method does not work properly.", expected.getName(), actual.getName());
        Assert.assertEquals("Find by Id method does not work properly.", expected.isImporter(), actual.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierService_findSupplierByIdNull_throwsException() {
        SupplierServiceModel model = this.supplierService.findSupplierById(null);
    }

}

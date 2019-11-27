package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartServiceTests {
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    private ModelMapper modelMapper;
    private PartService partService;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.partService = new PartServiceImpl(this.partRepository, this.modelMapper);
        SupplierServiceModel supplier = new SupplierServiceModel() {{
            setName("pesho");
            setImporter(true);
        }};
        this.supplierRepository.saveAndFlush(this.modelMapper.map(supplier, Supplier.class));
    }

    @Test
    public void partService_savePart_returnsCorrectModel() {
        PartServiceModel model = new PartServiceModel() {{
            setName("newPart");
            setPrice(new BigDecimal("25.50"));
            setSupplier(modelMapper.map(supplierRepository.findAll().get(0), SupplierServiceModel.class));
        }};
        PartServiceModel actual = this.partService.savePart(model);
        PartServiceModel expected =
                this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);
        Assert.assertEquals("Save method does not work properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Save method does not work properly.", expected.getName(), actual.getName());
        Assert.assertEquals("Save method does not work properly.", expected.getPrice(), actual.getPrice());
        Assert.assertEquals("Save method does not work properly.", expected.getSupplier().getId(), actual.getSupplier().getId());
    }

    @Test(expected = Exception.class)
    public void partService_savePartWithNull_throwsException() {
        this.partService.savePart(new PartServiceModel() {{
            setName(null);
            setPrice(null);
            setSupplier(null);
        }});
    }

    @Test
    public void partService_editPart_returnsEditedModel() {
        PartServiceModel model = new PartServiceModel() {{
            setName("part");
            setPrice(new BigDecimal("25.50"));
            setSupplier(modelMapper.map(supplierRepository.findAll().get(0), SupplierServiceModel.class));
        }};
        this.partRepository.saveAndFlush(this.modelMapper.map(model, Part.class));
        PartServiceModel edited = new PartServiceModel() {{
            setId(partRepository.findAll().get(0).getId());
            setName("newPart");
            setPrice(new BigDecimal("30.00"));
        }};
        PartServiceModel actual = this.partService.editPart(edited);
        PartServiceModel expected = this.modelMapper
                .map(this.partRepository.findAll().get(0), PartServiceModel.class);
        Assert.assertEquals("Edit method does not work properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Edit method does not work properly.", expected.getName(), actual.getName());
        Assert.assertEquals("Edit method does not work properly.", expected.getPrice(), actual.getPrice());
    }

    @Test(expected = Exception.class)
    public void partService_editPartWithoutId_throwsException() {
        this.partService.editPart(new PartServiceModel());
    }

    @Test
    public void partService_deletePart_deletesEntity() {
        PartServiceModel model = new PartServiceModel() {{
            setName("part");
            setPrice(new BigDecimal("25.50"));
            setSupplier(modelMapper.map(supplierRepository.findAll().get(0), SupplierServiceModel.class));
        }};
        this.partRepository.saveAndFlush(this.modelMapper.map(model, Part.class));
        PartServiceModel expected = this.modelMapper
                .map(this.partRepository.findAll().get(0), PartServiceModel.class);
        PartServiceModel actual = this.partService
                .deletePart(this.partRepository.findAll().get(0).getId());
        Assert.assertEquals("Delete method does not work properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Delete method does not work properly.", expected.getName(), actual.getName());
        Assert.assertEquals("Delete method does not work properly.", expected.getPrice(), actual.getPrice());
        Assert.assertEquals("Delete method does not work properly.", expected.getSupplier().getId(), actual.getSupplier().getId());
    }

    @Test(expected = Exception.class)
    public void partService_deletePartWithoutId_throwsException() {
        this.partService.deletePart(null);
    }

    @Test
    public void partService_findPartById_returnsSupplier() {
        PartServiceModel model = new PartServiceModel() {{
            setName("part");
            setPrice(new BigDecimal("25.50"));
            setSupplier(modelMapper.map(supplierRepository.findAll().get(0), SupplierServiceModel.class));
        }};
        this.partRepository.saveAndFlush(this.modelMapper.map(model, Part.class));
        PartServiceModel expected = this.modelMapper
                .map(this.partRepository.findAll().get(0), PartServiceModel.class);
        PartServiceModel actual = this.partService
                .findPartById(this.partRepository.findAll().get(0).getId());
        Assert.assertEquals("Find by Id method does not work properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Find by Id method does not work properly.", expected.getName(), actual.getName());
        Assert.assertEquals("Find by Id method does not work properly.", expected.getPrice(), actual.getPrice());
        Assert.assertEquals("Find by Id method does not work properly.", expected.getSupplier().getId(), actual.getSupplier().getId());
    }

    @Test(expected = Exception.class)
    public void partService_findPartByIdNull_throwsException() {
        PartServiceModel model = this.partService.findPartById(null);
    }

}
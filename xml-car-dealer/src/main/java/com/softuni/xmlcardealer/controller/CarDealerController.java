package com.softuni.xmlcardealer.controller;


import com.softuni.xmlcardealer.domain.dtos.SuppliersSeedDto;
import com.softuni.xmlcardealer.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Controller
public class CarDealerController implements CommandLineRunner {
    private final static String ROOT = System.getProperty("user.dir");
    private final static String SUPPLIER_XML_PATH = "\\src\\main\\resources\\XML\\suppliers.xml";


    private final SupplierService supplierService;

    @Autowired
    public CarDealerController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Override
    public void run(String... args) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(SuppliersSeedDto.class);
        File file = new File(ROOT + SUPPLIER_XML_PATH);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SuppliersSeedDto suppliersSeedDtos = ( SuppliersSeedDto) unmarshaller.unmarshal(file);
        this.supplierService.seedSuppliers(suppliersSeedDtos.getSuppliers());
    }
}

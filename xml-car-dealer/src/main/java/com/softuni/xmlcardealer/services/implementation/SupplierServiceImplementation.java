package com.softuni.xmlcardealer.services.implementation;

import com.softuni.xmlcardealer.domain.dtos.SupplierSeedDto;
import com.softuni.xmlcardealer.domain.entities.Supplier;
import com.softuni.xmlcardealer.repositories.SupplierRepository;
import com.softuni.xmlcardealer.services.SupplierService;
import com.softuni.xmlcardealer.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SupplierServiceImplementation implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public SupplierServiceImplementation(SupplierRepository supplierRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedSuppliers(List<SupplierSeedDto> supplierSeedDtos) {
        for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
            if (!validatorUtil.isValid(supplierSeedDto)) {
                this.validatorUtil.violations(supplierSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }
            PropertyMap<SupplierSeedDto,Supplier > productsCountMap = new PropertyMap<SupplierSeedDto,Supplier>() {

                @Override
                protected void configure() {
                    map().setIsImporter(Boolean.parseBoolean(source.getIsImporter()));

                }
            };
            Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
            this.supplierRepository.saveAndFlush(supplier);
        }
    }
}

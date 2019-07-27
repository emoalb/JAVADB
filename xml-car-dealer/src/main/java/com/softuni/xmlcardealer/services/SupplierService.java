package com.softuni.xmlcardealer.services;

import com.softuni.xmlcardealer.domain.dtos.SupplierSeedDto;
import com.softuni.xmlcardealer.domain.entities.Supplier;

import java.util.List;

public interface SupplierService {
    void seedSuppliers(List<SupplierSeedDto> supplierSeedDtos);
}

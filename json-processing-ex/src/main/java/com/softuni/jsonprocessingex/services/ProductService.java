package com.softuni.jsonprocessingex.services;

import com.softuni.jsonprocessingex.domain.dtos.ProductSeedDto;

public interface ProductService {
    void seedProducts(ProductSeedDto[] productSeedDtos);
}

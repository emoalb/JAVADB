package com.softuni.demo.services;

import com.softuni.demo.domain.dtos.ProductInRangeDto;
import com.softuni.demo.domain.dtos.ProductSeedDto;

import java.math.BigDecimal;
import java.util.List;


public interface ProductService {
    void seedProducts(List<ProductSeedDto> productSeedDtos);
    List<ProductInRangeDto> getProductsFromRange(BigDecimal startingPrice, BigDecimal endPrice);
}

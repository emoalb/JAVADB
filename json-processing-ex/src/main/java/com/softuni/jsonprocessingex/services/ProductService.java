package com.softuni.jsonprocessingex.services;

import com.softuni.jsonprocessingex.domain.dtos.ProductSeedDto;
import com.softuni.jsonprocessingex.domain.dtos.ProductsInRangeDto;


import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts(ProductSeedDto[] productSeedDtos);
    List<ProductsInRangeDto> getProductsFromRange(BigDecimal startingPrice, BigDecimal endPrice);
}

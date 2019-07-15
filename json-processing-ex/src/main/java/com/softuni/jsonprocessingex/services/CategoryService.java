package com.softuni.jsonprocessingex.services;


import com.softuni.jsonprocessingex.domain.dtos.CategoryDto;
import com.softuni.jsonprocessingex.domain.dtos.CategorySeedDto;

import java.util.List;

public interface CategoryService {
    void seedCategories(CategorySeedDto[] categorySeedDtos);

    List<CategoryDto> getCategoriesByProductsCount();
}

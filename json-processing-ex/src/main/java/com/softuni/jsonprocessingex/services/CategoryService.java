package com.softuni.jsonprocessingex.services;

import com.softuni.jsonprocessingex.domain.dtos.CategorySeedDto;

public interface CategoryService {
    void seedCategories(CategorySeedDto[] categorySeedDtos);
}

package com.softuni.demo.services;



import com.softuni.demo.domain.dtos.CategoryDto;
import com.softuni.demo.domain.dtos.CategorySeedDto;

import java.util.List;


public interface CategoryService {
    void seedCategories(List<CategorySeedDto> categorySeedDtos);

    List<CategoryDto> getCategoriesByProductsCount();
}

package com.softuni.jsonprocessingex.services.implementation;


import com.softuni.jsonprocessingex.domain.dtos.CategoryDto;
import com.softuni.jsonprocessingex.domain.dtos.CategorySeedDto;
import com.softuni.jsonprocessingex.domain.entities.Category;
import com.softuni.jsonprocessingex.domain.entities.Product;
import com.softuni.jsonprocessingex.repositories.CategoryRepository;
import com.softuni.jsonprocessingex.services.CategoryService;
import com.softuni.jsonprocessingex.util.ValidatorUtil;
import org.modelmapper.*;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImplementation implements CategoryService {
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImplementation(ValidatorUtil validatorUtil, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        for (CategorySeedDto categorySeedDto : categorySeedDtos) {
            if (!validatorUtil.isValid(categorySeedDto)) {
                this.validatorUtil.violations(categorySeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }
            Category category = this.modelMapper.map(categorySeedDto, Category.class);
            this.categoryRepository.saveAndFlush(category);
        }
    }

    BigDecimal avarageConverter(Set<Product> products) {

        BigDecimal val = new BigDecimal(0);
        int size = products.size();
        if (size > 0) {
          val  = totalRevenue(products);
            val = val.divide(BigDecimal.valueOf(size), 2, RoundingMode.HALF_UP);
        }
        return val;
    }

    BigDecimal totalRevenue(Set<Product> products) {

        BigDecimal val = new BigDecimal(0);
        int size = products.size();
        if (size > 0) {
            for (Product product : products) {
                val = val.add(product.getPrice());
            }

        }
        return val;
    }
    @Override
    public List<CategoryDto> getCategoriesByProductsCount() {

        PropertyMap<Category, CategoryDto> productsCountMap = new PropertyMap<Category, CategoryDto>() {

            @Override
            protected void configure() {
                map().setCategory(source.getName());

            }
        };
        this.modelMapper.addMappings(productsCountMap);
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryDto> categoryDtos = this.modelMapper.map(categories, new TypeToken<List<CategoryDto>>() {
        }.getType());
        categoryDtos.forEach((categoryDto) ->
        {
            categoryDto.setProductsCount(categories.get(categoryDtos.indexOf(categoryDto)).getProducts().size());
            categoryDto.setAveragePrice(avarageConverter(categories.get(categoryDtos.indexOf(categoryDto)).getProducts()));
            categoryDto.setTotalRevenue(totalRevenue(categories.get(categoryDtos.indexOf(categoryDto)).getProducts()));
        });

        return categoryDtos;
    }
}

package com.softuni.jsonprocessingex.services.implementation;

import com.softuni.jsonprocessingex.domain.dtos.CategorySeedDto;
import com.softuni.jsonprocessingex.domain.entities.Category;
import com.softuni.jsonprocessingex.repositories.CategoryRepository;
import com.softuni.jsonprocessingex.services.CategoryService;
import com.softuni.jsonprocessingex.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            if(!validatorUtil.isValid(categorySeedDto)){
                this.validatorUtil.violations(categorySeedDto)
                        .forEach(v-> System.out.println(v.getMessage()));
                continue;
            }
            Category category = this.modelMapper.map(categorySeedDto,Category.class);
            this.categoryRepository.saveAndFlush(category);
        }
    }
}

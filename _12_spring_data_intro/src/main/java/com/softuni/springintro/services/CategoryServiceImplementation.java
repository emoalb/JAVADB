package com.softuni.springintro.services;

import com.softuni.springintro.entities.Category;
import com.softuni.springintro.repositories.CategoryRepository;
import com.softuni.springintro.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
public class CategoryServiceImplementation implements CategoryService {
    private final  static String CATEGORY_PATH = "G:\\springintro\\src\\main\\resources\\files\\categories.txt";
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired

    public CategoryServiceImplementation(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }



    @Override
    public void seedCategory() throws IOException {
        if(this.categoryRepository.count()!=0){
            return;
        }
        String[] categories = this.fileUtil.fileContent(CATEGORY_PATH);
        for (String c : categories) {
            Category category = new Category();
            category.setName(c);
            this.categoryRepository.saveAndFlush(category);
        }

    }
}

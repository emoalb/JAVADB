package com.softuni.jsonprocessingex.controllers;

import com.google.gson.Gson;
import com.softuni.jsonprocessingex.domain.dtos.CategorySeedDto;
import com.softuni.jsonprocessingex.domain.dtos.ProductSeedDto;
import com.softuni.jsonprocessingex.domain.dtos.UserSeedDto;
import com.softuni.jsonprocessingex.services.CategoryService;
import com.softuni.jsonprocessingex.services.ProductService;
import com.softuni.jsonprocessingex.services.UserService;
import com.softuni.jsonprocessingex.util.FileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class ProductShopController implements CommandLineRunner {
    private final static String USER_JSON_PATH = "G:\\json-processing-ex\\src\\main\\resources\\JSON\\users.json";
    private final static String CATEGORY_JSON_PATH = "G:\\json-processing-ex\\src\\main\\resources\\JSON\\categories.json";
    private final static String PRODUCT_JSON_PATH = "G:\\json-processing-ex\\src\\main\\resources\\JSON\\products.json";
    private final Gson gson;
    private final FileIO fileIO;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public ProductShopController(Gson gson, FileIO fileIO, UserService userService, CategoryService categoryService, ProductService productService) {
        this.gson = gson;
        this.fileIO = fileIO;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        String content = this.fileIO.fileContent(USER_JSON_PATH);
        UserSeedDto[] userSeedDtos = this.gson.fromJson(content, UserSeedDto[].class);
        this.userService.seedUsers(userSeedDtos);
        content = this.fileIO.fileContent(CATEGORY_JSON_PATH);
        CategorySeedDto[] categorySeedDtos = this.gson.fromJson(content, CategorySeedDto[].class);
        this.categoryService.seedCategories(categorySeedDtos);
        content = this.fileIO.fileContent(PRODUCT_JSON_PATH);
        ProductSeedDto[] productSeedDtos = this.gson.fromJson(content, ProductSeedDto[].class);
        this.productService.seedProducts(productSeedDtos);
    }
}

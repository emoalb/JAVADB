package com.softuni.jsonprocessingex.controllers;

import com.google.gson.Gson;
import com.softuni.jsonprocessingex.domain.dtos.*;
import com.softuni.jsonprocessingex.domain.entities.User;
import com.softuni.jsonprocessingex.repositories.UserRepository;
import com.softuni.jsonprocessingex.services.CategoryService;
import com.softuni.jsonprocessingex.services.ProductService;
import com.softuni.jsonprocessingex.services.UserService;
import com.softuni.jsonprocessingex.util.FileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductShopController implements CommandLineRunner {
    private final static String USER_JSON_PATH = "G:\\json-processing-ex\\src\\main\\resources\\JSON\\users.json";
    private final static String CATEGORY_JSON_PATH = "G:\\json-processing-ex\\src\\main\\resources\\JSON\\categories.json";
    private final static String PRODUCT_JSON_PATH = "G:\\json-processing-ex\\src\\main\\resources\\JSON\\products.json";
    private final static String PRODUCTS_IN_RANGE_PATH = "G:\\json-processing-ex\\src\\main\\resources\\Output\\products-in-range.json";
    private final static String USERS_SOLD_PRODUCTS_PATH = "G:\\json-processing-ex\\src\\main\\resources\\Output\\users-sold-products.json";
    private final static String CATEGORIES_BY_PRODUCTS_COUNT_PATH = "G:\\json-processing-ex\\src\\main\\resources\\Output\\categories-by-products.json";
    private final Gson gson;
    private final FileIO fileIO;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public ProductShopController(Gson gson, FileIO fileIO, UserService userService, CategoryService categoryService, ProductService productService, UserRepository userRepository) {
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
        List<ProductsInRangeDto> productsInRangeDtos = this.productService.getProductsFromRange(new BigDecimal(500), new BigDecimal(1000));
        String output = this.gson.toJson(productsInRangeDtos);
        try {
            this.fileIO.writeToJSON(PRODUCTS_IN_RANGE_PATH, output);
            System.out.println("Successful write to file " + PRODUCTS_IN_RANGE_PATH);
        } catch (Exception e) {
            System.out.println("Failed to write to file");
        }

        List<UserWithSoldProductsDto> userWithSoldProductsDtos = this.userService.getAllUsersWithSoldProduct();
        output = this.gson.toJson(userWithSoldProductsDtos);
        try {
            this.fileIO.writeToJSON(USERS_SOLD_PRODUCTS_PATH, output);
            System.out.println("Successful write to file " + USERS_SOLD_PRODUCTS_PATH);
        } catch (Exception e) {
            System.out.println("Failed to write to file");
        }

        List<CategoryDto> categoryDtos = this.categoryService.getCategoriesByProductsCount();
        output = this.gson.toJson(categoryDtos);
        try {
            this.fileIO.writeToJSON(CATEGORIES_BY_PRODUCTS_COUNT_PATH, output);
            System.out.println("Successful write to file " + CATEGORIES_BY_PRODUCTS_COUNT_PATH);
        } catch (Exception e) {
            System.out.println("Failed to write to file");
        }
    }
}

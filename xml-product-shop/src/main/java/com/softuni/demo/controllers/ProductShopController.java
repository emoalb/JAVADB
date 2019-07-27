package com.softuni.demo.controllers;

import com.softuni.demo.domain.dtos.*;
import com.softuni.demo.repositories.UserRepository;
import com.softuni.demo.services.CategoryService;
import com.softuni.demo.services.ProductService;
import com.softuni.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.math.BigDecimal;

@Controller
public class ProductShopController implements CommandLineRunner {
    private final static String ROOT = System.getProperty("user.dir");
    private final static String USER_XML_PATH = "\\src\\main\\resources\\XML\\users.xml";
    private final static String CATEGORY_XML_PATH = "\\src\\main\\resources\\XML\\categories.xml";
    private final static String PRODUCT_XML_PATH = "\\src\\main\\resources\\XML\\products.xml";
    private static final String PRODUCTS_IN_RANGE_XML_PATH = "\\src\\main\\resources\\OUT\\products-in-range.xml";
    private static final String PUSERS_WITH_SOLD_PRODUCTS_XML_PATH = "\\src\\main\\resources\\OUT\\users-sold-products.xml";
    private static final String CATEGORY_BY_PRODUCTS_XML_PATH = "\\src\\main\\resources\\OUT\\categories-by-products.xml";


    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public ProductShopController(UserService userService, CategoryService categoryService, ProductService productService, UserRepository userRepository) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;

    }

    @Override
    public void run(String... args) throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(UsersSeedDto.class);
        File file = new File(ROOT + USER_XML_PATH);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        UsersSeedDto userSeedDtos = (UsersSeedDto) unmarshaller.unmarshal(file);
        this.userService.seedUsers(userSeedDtos.getUserSeedDtos());


        jaxbContext = JAXBContext.newInstance(CategoriesSeedDto.class);
        unmarshaller = jaxbContext.createUnmarshaller();
        file = new File(ROOT + CATEGORY_XML_PATH);
        CategoriesSeedDto categoriesSeedDto = (CategoriesSeedDto) unmarshaller.unmarshal(file);
        this.categoryService.seedCategories(categoriesSeedDto.getCategories());

        jaxbContext = JAXBContext.newInstance(ProductsSeedDto.class);
        unmarshaller = jaxbContext.createUnmarshaller();
        file = new File(ROOT + PRODUCT_XML_PATH);
        ProductsSeedDto productsSeedDto = (ProductsSeedDto) unmarshaller.unmarshal(file);
        this.productService.seedProducts(productsSeedDto.getProductSeedDtos());


        jaxbContext = JAXBContext.newInstance(ProductsInRangeDto.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        file = new File(ROOT + PRODUCTS_IN_RANGE_XML_PATH);
        try {
            file.createNewFile();

            ProductsInRangeDto productsInRangeDto = new ProductsInRangeDto();
            productsInRangeDto.setProductInRangeDtos(productService.getProductsFromRange(new BigDecimal(500), new BigDecimal(1500.5)));
            jaxbMarshaller.marshal(productsInRangeDto, file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        jaxbContext = JAXBContext.newInstance(UsersWithSoldProductDto.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        file = new File(ROOT + PUSERS_WITH_SOLD_PRODUCTS_XML_PATH);
        try {
            file.createNewFile();

            UsersWithSoldProductDto usersWithSoldProductDto = new UsersWithSoldProductDto();
            usersWithSoldProductDto.setUserWithSoldProductsDtos(this.userService.getAllUsersWithSoldProduct());
            jaxbMarshaller.marshal(usersWithSoldProductDto, file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        jaxbContext = JAXBContext.newInstance(CategoriesDto.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        file = new File(ROOT + CATEGORY_BY_PRODUCTS_XML_PATH);
        try {
            file.createNewFile();

            CategoriesDto categoriesDto = new CategoriesDto();
            categoriesDto.setCategoryDtos(categoryService.getCategoriesByProductsCount());
            jaxbMarshaller.marshal(categoriesDto, file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

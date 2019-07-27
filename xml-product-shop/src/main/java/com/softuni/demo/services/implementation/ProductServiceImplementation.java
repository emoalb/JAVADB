package com.softuni.demo.services.implementation;

import com.softuni.demo.domain.dtos.ProductInRangeDto;
import com.softuni.demo.domain.dtos.ProductSeedDto;
import com.softuni.demo.domain.entities.Category;
import com.softuni.demo.domain.entities.Product;
import com.softuni.demo.domain.entities.User;
import com.softuni.demo.repositories.CategoryRepository;
import com.softuni.demo.repositories.ProductRepository;
import com.softuni.demo.repositories.UserRepository;
import com.softuni.demo.services.ProductService;
import com.softuni.demo.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class ProductServiceImplementation implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImplementation(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedProducts(List<ProductSeedDto>  productSeedDtos) {
      for (ProductSeedDto productSeedDto : productSeedDtos) {
          productSeedDto.setCategories(this.getRandomCategories());
          User user = getRandomUser();
          productSeedDto.setSeller(user);
          if (user.getId() % 3 == 0) {
              productSeedDto.setBuyer(null);
          } else {
              user = getRandomUser();
              productSeedDto.setBuyer(user);
          }
            if (!validatorUtil.isValid(productSeedDto)) {
                this.validatorUtil.violations(productSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }
            Product product = this.modelMapper.map(productSeedDto, Product.class);
            this.productRepository.saveAndFlush(product);
        }
    }

    @Override
    public List<ProductInRangeDto> getProductsFromRange(BigDecimal startingPrice, BigDecimal endPrice) {
        if(startingPrice.compareTo(endPrice) > 0){
            throw new IllegalArgumentException("Starting price has bigger value than ending price");
        }
        List<Product> productsFromRange = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(startingPrice, endPrice);
        List<ProductInRangeDto> exportProducts = new ArrayList<>();
        productsFromRange.forEach(p -> {
            String fullName;
            if (p.getSeller().getFirstName() == null) {
                fullName = p.getSeller().getLastName();
            } else {
                fullName = p.getSeller().getFirstName() + " " + p.getSeller().getLastName();
            }

            ProductInRangeDto productsInRangeDto = new ProductInRangeDto(p.getName(), p.getPrice(), fullName);
            exportProducts.add(productsInRangeDto);
        });
        return exportProducts;
    }
    private User getRandomUser() {
        Set<User> allUsers = new HashSet<>(this.userRepository.findAll());
        Random random = new Random();
        int index = random.nextInt(allUsers.size() - 1);
        return (User) allUsers.toArray()[index];
    }

    private Category getRandomCategory() {
        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepository.count() - 1) + 1;
        return this.categoryRepository.findById(index).orElse(null);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        categories.add(getRandomCategory());
        categories.add(getRandomCategory());
        return categories;
    }
}

package com.softuni.demo.services.implementation;


import com.softuni.demo.domain.dtos.ProductDto;
import com.softuni.demo.domain.dtos.SoldProductsDto;
import com.softuni.demo.domain.dtos.UserSeedDto;
import com.softuni.demo.domain.dtos.UserWithSoldProductsDto;
import com.softuni.demo.domain.entities.Product;
import com.softuni.demo.domain.entities.User;
import com.softuni.demo.repositories.UserRepository;
import com.softuni.demo.services.UserService;
import com.softuni.demo.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImplementation implements UserService {
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(ValidatorUtil validatorUtil, ModelMapper modelMapper, UserRepository userRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void seedUsers(List<UserSeedDto> userSeedDtos) {
        for (UserSeedDto userSeedDto : userSeedDtos) {
            if (!validatorUtil.isValid(userSeedDto)) {
                this.validatorUtil.violations(userSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }
            User user = this.modelMapper.map(userSeedDto, User.class);
            this.userRepository.saveAndFlush(user);
        }
    }

    @Override
    public List<UserWithSoldProductsDto> getAllUsersWithSoldProduct() {
        List<User> users = this.userRepository.findAll();
        List<UserWithSoldProductsDto> usersWithSoldProductsDtos = new ArrayList<>();
        users.forEach(
                u -> {
                    UserWithSoldProductsDto usersWithSoldProductsDto = new UserWithSoldProductsDto();
                    if (u.getFirstName() != null) usersWithSoldProductsDto.setFirstName(u.getFirstName());
                    usersWithSoldProductsDto.setLastName(u.getLastName());
                    u.getSellingProduct().forEach(p -> {
                        if (p != null && p.getBuyer() != null) {
                            SoldProductsDto  soldProductsDto = usersWithSoldProductsDto.getSoldProductsDto();
                            List<ProductDto> productSet;

                            if (soldProductsDto == null) {
                                soldProductsDto = new SoldProductsDto();
                                productSet = new ArrayList<>();
                                soldProductsDto.setSoldProducts(productSet);
                                usersWithSoldProductsDto.setSoldProductsDto(soldProductsDto);
                            }else{
                                productSet = usersWithSoldProductsDto.getSoldProductsDto().getSoldProducts();
                            }
                            ProductDto productDto = new ProductDto(p.getName(), p.getPrice(), p.getBuyer().getFirstName(), p.getBuyer().getLastName());
                            if (validatorUtil.isValid(productDto)) {

                                productSet.add(productDto);
                                usersWithSoldProductsDto.getSoldProductsDto().setSoldProducts(productSet);
                            }
                        }
                    });
                    if (validatorUtil.isValid(usersWithSoldProductsDto)) {

                        if (usersWithSoldProductsDto.getSoldProductsDto() != null && usersWithSoldProductsDto.getSoldProductsDto().getSoldProducts().size() != 0) {
                            usersWithSoldProductsDtos.add(usersWithSoldProductsDto);
                        }
                    }
                }
        );
        return usersWithSoldProductsDtos;
    }
}

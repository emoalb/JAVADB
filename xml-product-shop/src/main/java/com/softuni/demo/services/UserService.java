package com.softuni.demo.services;

import com.softuni.demo.domain.dtos.UserSeedDto;
import com.softuni.demo.domain.dtos.UserWithSoldProductsDto;

import java.util.List;


public interface UserService {
    void seedUsers(List<UserSeedDto> userSeedDtos);
    List<UserWithSoldProductsDto> getAllUsersWithSoldProduct();

}

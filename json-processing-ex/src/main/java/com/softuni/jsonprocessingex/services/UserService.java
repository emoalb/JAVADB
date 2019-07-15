package com.softuni.jsonprocessingex.services;

import com.softuni.jsonprocessingex.domain.dtos.UserSeedDto;
import com.softuni.jsonprocessingex.domain.dtos.UserWithSoldProductsDto;

import java.util.List;

public interface UserService {
    void seedUsers(UserSeedDto[] userSeedDtos);

    List<UserWithSoldProductsDto> getAllUsersWithSoldProduct();

}

package com.softuni.jsonprocessingex.services;

import com.softuni.jsonprocessingex.domain.dtos.UserSeedDto;

public interface UserService {
    void seedUsers(UserSeedDto[] userSeedDtos);
}

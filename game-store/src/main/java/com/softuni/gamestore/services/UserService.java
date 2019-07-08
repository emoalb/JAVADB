package com.softuni.gamestore.services;

import com.softuni.gamestore.domain.dtos.UserRegisterDto;

public interface UserService {
    String regitreUser(UserRegisterDto registerDto);
}

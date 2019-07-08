package com.softuni.gamestore.services;

import com.softuni.gamestore.domain.dtos.UserLoginDto;
import com.softuni.gamestore.domain.dtos.UserRegisterDto;

public interface UserService {
    String registerUser(UserRegisterDto registerDto);

    String loginUser(UserLoginDto userLoginDto);

    String logoutUser();
}

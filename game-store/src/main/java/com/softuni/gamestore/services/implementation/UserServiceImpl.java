package com.softuni.gamestore.services.implementation;

import com.softuni.gamestore.domain.dtos.UserRegisterDto;
import com.softuni.gamestore.domain.entities.User;
import com.softuni.gamestore.repositories.UserRepository;
import com.softuni.gamestore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public String regitreUser(UserRegisterDto registerDto) {
        StringBuilder sb = new StringBuilder();

        User user = this.modelMapper.map(registerDto, User.class);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        System.out.println();


        return null;
    }
}

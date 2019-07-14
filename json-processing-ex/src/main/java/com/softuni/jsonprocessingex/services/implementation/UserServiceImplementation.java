package com.softuni.jsonprocessingex.services.implementation;

import com.softuni.jsonprocessingex.domain.dtos.UserSeedDto;
import com.softuni.jsonprocessingex.domain.entities.User;
import com.softuni.jsonprocessingex.repositories.UserRepository;
import com.softuni.jsonprocessingex.services.UserService;
import com.softuni.jsonprocessingex.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public void seedUsers(UserSeedDto[] userSeedDtos) {
for(UserSeedDto userSeedDto: userSeedDtos){
    if(!validatorUtil.isValid(userSeedDto)){
       this.validatorUtil.violations(userSeedDto)
               .forEach(v-> System.out.println(v.getMessage()));
    continue;
    }
    User user = this.modelMapper.map(userSeedDto,User.class);
    this.userRepository.saveAndFlush(user);
}
    }
}

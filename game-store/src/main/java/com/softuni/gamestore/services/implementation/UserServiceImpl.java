package com.softuni.gamestore.services.implementation;

import com.softuni.gamestore.domain.dtos.UserLoginDto;
import com.softuni.gamestore.domain.dtos.UserRegisterDto;
import com.softuni.gamestore.domain.entities.Role;
import com.softuni.gamestore.domain.entities.User;
import com.softuni.gamestore.repositories.UserRepository;
import com.softuni.gamestore.services.UserService;
import com.softuni.gamestore.services.UserSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    private UserSession userSession;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
        this.userSession = UserSession.getInstance();
    }

    @Override
    public String registerUser(UserRegisterDto registerDto) {
        StringBuilder sb = new StringBuilder();

        User user = this.modelMapper.map(registerDto, User.class);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User dbUser = this.userRepository.findByEmail(registerDto.getEmail()).orElse(null);
        if (dbUser != null) {
            return sb.append("User is already present!").append(System.lineSeparator()).toString();
        }
        if (violations.size() > 0) {
            for (ConstraintViolation<User> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        } else {
            if (this.userRepository.count() == 0) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.USER);
            }

            try {
                this.userRepository.saveAndFlush(user);
                sb.append(String.format("%s was registered", user.getFullName()));
            } catch (Exception e) {
                sb.append(e.getMessage());
            }

        }

        return sb.toString();
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {
        StringBuilder sb = new StringBuilder();

        if (this.userSession.getUsername() != null) {
            return sb.append("User is already logged in").toString();
        }

        User user = this.userRepository.findByEmail(userLoginDto.getEmail()).orElse(null);

        if (user == null) {
            return sb.append("Incorrect username/password").toString();
        } else {
            if (!user.getPassword().equals(userLoginDto.getPassword())) {
                return sb.append("Incorrect username/password").toString();
            }
            sb.append(String.format("Successfully logged in %s", user.getFullName()));
        }
        this.userSession.setUsername(user.getFullName());
        if (user.getRole() == Role.ADMIN) this.userSession.setRole(Role.ADMIN);
        return sb.toString();
    }

    @Override
    public String logoutUser() {
        StringBuilder sb = new StringBuilder();
        if (this.userSession.getUsername() == null) {
            return sb.append("Cannot log out. No user was logged in.").toString();
        }
        sb.append("User ").append(this.userSession.getUsername()).append(" successfully logged out");
        this.userSession.setUsername(null);
        return sb.toString();
    }
}

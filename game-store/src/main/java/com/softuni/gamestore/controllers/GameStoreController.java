package com.softuni.gamestore.controllers;

import com.softuni.gamestore.domain.dtos.UserRegisterDto;
import com.softuni.gamestore.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {
    private final UserService userService;

    public GameStoreController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            String[] params = scanner.nextLine().split("\\|");
            switch (params[0]) {
                case "RegisterUser":
                    UserRegisterDto userRegisterDto =
                            new UserRegisterDto(params[1],params[2],params[3],params[4]);
                    this.userService.regitreUser(userRegisterDto);
                    break;
                case "LoginUser":
                    break;
                case "LogoutUser":
                    break;
            }
        }
    }
}

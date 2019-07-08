package com.softuni.gamestore.controllers;

import com.softuni.gamestore.domain.dtos.GameAddDto;
import com.softuni.gamestore.domain.dtos.UserLoginDto;
import com.softuni.gamestore.domain.dtos.UserRegisterDto;
import com.softuni.gamestore.services.GameService;
import com.softuni.gamestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public GameStoreController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            String[] params = scanner.nextLine().split("\\|");
            switch (params[0]) {
                case "RegisterUser":
                    UserRegisterDto userRegisterDto =
                            new UserRegisterDto(params[1], params[2], params[3], params[4]);
                    System.out.println(this.userService.registerUser(userRegisterDto));
                    break;
                case "LoginUser":
                    UserLoginDto userLoginDto = new UserLoginDto(params[1], params[2]);
                    System.out.println(this.userService.loginUser(userLoginDto));
                    break;
                case "LogoutUser":
                    System.out.println(this.userService.logoutUser());
                    break;
                case "AddGame":
                    GameAddDto gameAddDto = new GameAddDto(params[1], params[4], params[5], params[7], Double.parseDouble(params[3]),
                            new BigDecimal(params[2]), LocalDate.parse(params[7], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                  System.out.println(this.gameService.addGame(gameAddDto));
                    break;
                case "EditGame":
                    break;
                case "DeleteGame":
                    break;

            }
        }
    }
}

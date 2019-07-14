package com.softuni.gamestore.services.implementation;

import com.softuni.gamestore.domain.dtos.GameAddDto;
import com.softuni.gamestore.domain.entities.Game;
import com.softuni.gamestore.domain.entities.Role;
import com.softuni.gamestore.repositories.GameRepository;
import com.softuni.gamestore.services.GameService;
import com.softuni.gamestore.services.UserSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private UserSession userSession;
    private ModelMapper modelMapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.modelMapper = new ModelMapper();
        this.userSession = UserSession.getInstance();
    }

    @Override
    public String addGame(GameAddDto gameAddDto) {
        StringBuilder sb = new StringBuilder();

        if (this.userSession.getUsername() == null) {
            return sb.append("Not logged in").toString();
        }
        if (this.userSession.getRole() != Role.ADMIN) {
            return sb.append(this.userSession.getUsername()).append(" is not admin").toString();
        }
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Game game = this.modelMapper.map(gameAddDto, Game.class);
        Set<ConstraintViolation<Game>> violations = validator.validate(game);
        if (violations.size() > 0) {
            for (ConstraintViolation<Game> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
            return sb.toString();
        }
        try {
            this.gameRepository.saveAndFlush(game);
        } catch (Exception e) {
            return sb.append(e.getMessage()).toString();
        }

        return sb.append("Added ").append(game.getTitle()).toString();

    }

    @Override
    public String findAllGames() {
        StringBuilder sb = new StringBuilder();
        List<Game> allGames = this.gameRepository.findAll();
        if (allGames.size() == 0) {
            return sb.append("No games found!").toString();
        }
        allGames.forEach(game -> {
            sb.append(game.getTitle()).append(" ").append(game.getPrice()).append(System.lineSeparator());
        });
        return sb.toString();
    }

    @Override
    public String deleteGame(int id) {

        StringBuilder sb = new StringBuilder();
        if (this.userSession.getUsername() == null) {
            return sb.append("Not logged in").toString();
        }
        if (this.userSession.getRole() != Role.ADMIN) {
            return sb.append(this.userSession.getUsername()).append(" is not admin").toString();
        }
        Game game = this.gameRepository.getById(id).orElse(null);
        if (game == null) {
            return sb.append("Cannot find game by given Id").toString();
        }
        sb.append("Deleted ").append(game.getTitle());
        try {
            this.gameRepository.delete(game);
        } catch (Exception e) {
            return sb.append(e.getMessage()).toString();
        }

        return sb.toString();
    }

    @Override
    public String printGameDetails(String title) {
        StringBuilder sb = new StringBuilder();
        Game game = this.gameRepository.getByTitle(title).orElse(null);
        if (game == null) {
            return sb.append("No game with selected title was found").toString();
        }
        sb.append("Title: ").append(game.getTitle()).append(System.lineSeparator());
        sb.append("Price: ").append(game.getPrice()).append(System.lineSeparator());
        sb.append("Description: ").append(game.getDescription()).append(System.lineSeparator());
        sb.append("Release date: ").append(game.getReleaseDate()).append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public String editGame(Integer id, String[] params) {
        StringBuilder sb = new StringBuilder();
        if (this.userSession.getUsername() == null) {
            return sb.append("Not logged in").toString();
        }
        if (this.userSession.getRole() != Role.ADMIN) {
            return sb.append(this.userSession.getUsername()).append(" is not admin").toString();
        }
        Game game = this.gameRepository.getById(id).orElse(null);
        if (game == null) {
            return sb.append("Cannot find game by given Id").toString();
        }
        String title = game.getTitle();
        for (int i = 0; i < params.length; i++) {
            String field = params[i].split("=")[0];
            // System.out.println(field);
            String value = params[i].split("=")[1];
            // System.out.println(value);
            try {
                Field gameField = game.getClass().getDeclaredField(field);
                gameField.setAccessible(true);
                if (field.equals("price")) {
                    gameField.set(game, new BigDecimal(value));
                } else if (field.equals("size")) {
                    gameField.set(game, Double.parseDouble(value));
                } else if (field.equals("releaseDate")) {
                    gameField.set(game, LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                } else {
                    gameField.set(game, value);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return sb.append(e.getMessage()).toString();
            }
            try {
                this.gameRepository.saveAndFlush(game);
            } catch (Exception e) {
                return sb.append(e.getMessage()).toString();
            }

        }
        return sb.append("Edited ").append(title).toString();

    }
}

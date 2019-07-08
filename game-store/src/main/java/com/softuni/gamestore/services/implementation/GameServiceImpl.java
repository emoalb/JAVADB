package com.softuni.gamestore.services.implementation;

import com.softuni.gamestore.domain.dtos.GameAddDto;
import com.softuni.gamestore.domain.entities.Game;
import com.softuni.gamestore.domain.entities.Role;
import com.softuni.gamestore.domain.entities.User;
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

        this.gameRepository.saveAndFlush(game);
        return sb.append("Added ").append(game.getTitle()).toString();

    }
}

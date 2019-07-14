package com.softuni.gamestore.services;

import com.softuni.gamestore.domain.dtos.GameAddDto;

public interface GameService {
    String addGame(GameAddDto gameAddDto);

    String findAllGames();

    String deleteGame(int id);

    String printGameDetails(String title);

    String editGame(Integer id, String[] params);
}

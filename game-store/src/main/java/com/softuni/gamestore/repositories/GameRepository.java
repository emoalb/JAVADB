package com.softuni.gamestore.repositories;

import com.softuni.gamestore.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game,Integer> {
Optional<Game> getById(Integer id);
Optional<Game> getByTitle(String title);
}

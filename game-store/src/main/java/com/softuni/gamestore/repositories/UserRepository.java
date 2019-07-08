package com.softuni.gamestore.repositories;

import com.softuni.gamestore.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}

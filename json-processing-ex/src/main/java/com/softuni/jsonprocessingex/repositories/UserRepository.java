package com.softuni.jsonprocessingex.repositories;

import com.softuni.jsonprocessingex.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

}

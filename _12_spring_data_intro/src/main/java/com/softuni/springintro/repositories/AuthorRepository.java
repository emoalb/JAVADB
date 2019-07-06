package com.softuni.springintro.repositories;

import com.softuni.springintro.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Set;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
  List<Author> findAll();

  Author findByFirstNameAndLastName(String firstName,String lastName);


}

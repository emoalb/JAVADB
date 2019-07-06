package com.softuni.springintro.services;

import com.softuni.springintro.entities.Author;
import com.softuni.springintro.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;
    List<String> findAllTitles();

    List<String> findAllAuthors();

    List<String> finsAllBooksByAuthor();
}

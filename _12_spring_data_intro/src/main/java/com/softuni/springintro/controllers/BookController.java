package com.softuni.springintro.controllers;

import com.softuni.springintro.services.AuthorService;
import com.softuni.springintro.services.BookService;
import com.softuni.springintro.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController implements CommandLineRunner {
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public BookController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategory();
        this.bookService.seedBooks();

        List<String> titles = this.bookService.findAllTitles();
        titles.forEach(System.out::println);


        List<String> authors = this.bookService.findAllAuthors();
        authors.forEach(System.out::println);

        List<String> authorsBookCount = this.authorService.findAllAuthors();
        authorsBookCount.forEach(System.out::println);

        List<String> findGeorgePowellBooks = this.bookService.finsAllBooksByAuthor();
        findGeorgePowellBooks.forEach(System.out::println);

    }
}

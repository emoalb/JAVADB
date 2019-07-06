package com.softuni.springintro.services;

import com.softuni.springintro.entities.Author;
import com.softuni.springintro.repositories.AuthorRepository;
import com.softuni.springintro.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
public class AuthorServiceImplementation implements AuthorService {
    private final static String AUTHOR_PATH = "G:\\springintro\\src\\main\\resources\\files\\authors.txt";
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImplementation(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }
        String[] authors = this.fileUtil.fileContent(AUTHOR_PATH);

        for (String a : authors) {
            String[] authorParam = a.split("\\s+");
            Author author = new Author();
            author.setFirstName(authorParam[0]);
            author.setLastName(authorParam[1]);
            this.authorRepository.saveAndFlush(author);
        }

    }

    @Override
    public List<String> findAllAuthors() {
        List<Author> authors = new ArrayList<>(this.authorRepository.findAll());
        authors.sort((o1, o2) -> o1.getBooks().size() < o2.getBooks().size() ? 0 : -1);
        List<String> namesAndCount = new ArrayList<>();
        authors.forEach(a -> namesAndCount.add( a.getFirstName() + " " + a.getLastName() + " " + a.getBooks().size()));

        return namesAndCount;
    }
}

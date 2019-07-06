package com.softuni.springintro.services;

import com.softuni.springintro.entities.*;
import com.softuni.springintro.repositories.AuthorRepository;
import com.softuni.springintro.repositories.BookRepository;
import com.softuni.springintro.repositories.CategoryRepository;
import com.softuni.springintro.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImplementation implements BookService {
    private final static String BOOKS_PATH = "G:\\springintro\\src\\main\\resources\\files\\books.txt";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImplementation(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }
        String[] books = this.fileUtil.fileContent(BOOKS_PATH);
        for (String b : books) {

            String[] bookParam = b.split("\\s+");

            Book book = new Book();
            book.setAuthor(this.randomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(bookParam[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(bookParam[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            book.setCopies(Integer.parseInt(bookParam[2]));
            BigDecimal price = new BigDecimal(bookParam[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(bookParam[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();

            for (int i = 5; i <= bookParam.length - 1; i++) {
                title.append(bookParam[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            book.setCategories(this.randomCategories());
            this.bookRepository.saveAndFlush(book);
        }

    }

    @Override
    public List<String> findAllTitles() {
        LocalDate releaseDate = LocalDate.parse("31/12/2000",DateTimeFormatter.ofPattern("d/M/yyyy"));
        List<String> titles = this.bookRepository.findAllByReleaseDateAfter(releaseDate)
                .stream().map(b->b.getTitle()).collect(Collectors.toList());
        return titles;
    }

    @Override
    public List<String> findAllAuthors() {
        LocalDate releaseDate = LocalDate.parse("1/1/1990",DateTimeFormatter.ofPattern("d/M/yyyy"));
        List<String> authors = this.bookRepository.findAllByReleaseDateBefore(releaseDate)
                .stream().map(b->String.format("%s %s",b.getAuthor().getFirstName(),b.getAuthor().getLastName()))
                .collect(Collectors.toList());
        return authors;
    }

    @Override
    public List<String> finsAllBooksByAuthor() {
        Author author = this.authorRepository.findByFirstNameAndLastName("George","Powell");
        List<Book> books = this.bookRepository.findAllByAuthorIdOrderByReleaseDateDescTitleAsc(author.getId());
        List<String> result  = new ArrayList<>();
        books.forEach(book -> {
            StringBuilder sb = new StringBuilder();
            sb.append(book.getTitle()).append(" ").append(book.getReleaseDate()).append(" ").append(book.getCopies());
            result.add(sb.toString());
        });

        return result;
    }

    private Author randomAuthor() {
        Random random = new Random();
        int index = random.nextInt((int) this.authorRepository.count()) + 1;
        return this.authorRepository.getOne(index);
    }


    private Category randomCategory() {
        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepository.count()) + 1;
        return this.categoryRepository.getOne(index);
    }

    private Set<Category> randomCategories() {
        Set<Category> categories = new LinkedHashSet<>();
        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepository.count()) + 1;
        for (int i = 0; i < index; i++) {
            categories.add(randomCategory());
        }
        return categories;
    }
}

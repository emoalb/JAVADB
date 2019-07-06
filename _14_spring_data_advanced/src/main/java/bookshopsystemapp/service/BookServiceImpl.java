package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.*;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.repository.BookRepository;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static String BOOKS_FILE_PATH = "C:\\spring-data-advanced\\src\\main\\resources\\files\\books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
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

        String[] booksFileContent = this.fileUtil.getFileContent(BOOKS_FILE_PATH);
        for (String line : booksFileContent) {
            String[] lineParams = line.split("\\s+");

            Book book = new Book();
            book.setAuthor(this.getRandomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(lineParams[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(lineParams[2]);
            book.setCopies(copies);

            BigDecimal price = new BigDecimal(lineParams[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(lineParams[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();
            for (int i = 5; i < lineParams.length; i++) {
                title.append(lineParams[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            Set<Category> categories = this.getRandomCategories();
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> getAllBooksTitlesAfter() {
        List<Book> books = this.bookRepository.findAllByReleaseDateAfter(LocalDate.parse("2000-12-31"));

        return books.stream().map(b -> b.getTitle()).collect(Collectors.toList());
    }

    @Override
    public Set<String> getAllAuthorsWithBookBefore() {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse("1990-01-01"));

        return books.stream().map(b -> String.format("%s %s", b.getAuthor().getFirstName(), b.getAuthor().getLastName())).collect(Collectors.toSet());
    }

    private Author getRandomAuthor() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.authorRepository.count() - 1)) + 1;

        return this.authorRepository.findById(randomId).orElse(null);
    }

    public List<Book> getAllBooksByAgeRestriction(String ageRestriction) {
        List<Book> books = this.bookRepository.
                findAllByAgeRestriction(AgeRestriction.valueOf(ageRestriction.toUpperCase()));
        return books;
    }

    @Override
    public List<Book> getAllGoldenEditionBooksWithCopiesLessThan5000() {
        List<Book> books = this.bookRepository.findAllByEditionTypeAndCopiesIsLessThan(EditionType.valueOf("GOLD"), 5000);
        return books;
    }

    @Override
    public List<Book> getAllByPriceLowerThan5AndGreaterThan40() {

        List<Book> books = this.bookRepository.findAllByPriceIsLessThanOrPriceIsGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40));

        return books;
    }

    @Override
    public List<Book> getAllAreNotReleasedThisYear(String yearStr) {
        Year year = Year.parse(yearStr);
        LocalDate before = year.atDay(1);
        LocalDate after = year.atMonth(12).atDay(31);
        List<Book> books = this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(before, after);
        return books;
    }


    @Override
    public String booksContainingIgnoreCase(String sequence) {

        List<String> books = this.bookRepository.findAllByTitleIsContainingIgnoringCase(sequence).stream().map(book -> String.format("%s", book.getTitle())).collect(Collectors.toList());
        return String.join(System.lineSeparator(), books);
    }

    @Override
    public String getBooksReleasedBefore(String date) {
        List<String> books = this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))).stream().map(book -> String.format("%s %s %.2f", book.getTitle(), book.getEditionType(), book.getPrice())).collect(Collectors.toList());
        return String.join(System.lineSeparator(), books);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int length = random.nextInt(5);

        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();

            categories.add(category);
        }

        return categories;
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;

        return this.categoryRepository.findById(randomId).orElse(null);
    }


}

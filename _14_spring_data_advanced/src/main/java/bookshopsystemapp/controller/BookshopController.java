package bookshopsystemapp.controller;

import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
        this.bookService.getAllBooksByAgeRestriction("mInor").forEach(book -> System.out.println(book.getTitle()));
        this.bookService.getAllGoldenEditionBooksWithCopiesLessThan5000().forEach(book -> System.out.println(book.getTitle()));
        this.bookService.getAllByPriceLowerThan5AndGreaterThan40().forEach(book -> System.out.println(book.getTitle() + " - $" + book.getPrice()));
        this.bookService.getAllAreNotReleasedThisYear("1998").forEach(book -> System.out.println(book.getTitle()));
        System.out.println(this.authorService.authorsEndingWith("rge"));
        System.out.println(this.bookService.booksContainingIgnoreCase("sK"));
        System.out.println(this.bookService.getBooksReleasedBefore("12-04-1992"));
        System.out.println(this.bookService.getBooksReleasedBefore("30-12-1989"));
    }
}

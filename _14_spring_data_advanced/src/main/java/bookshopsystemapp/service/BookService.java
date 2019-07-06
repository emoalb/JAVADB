package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Book;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> getAllBooksTitlesAfter();

    Set<String> getAllAuthorsWithBookBefore();

    List<Book> getAllBooksByAgeRestriction(String ageRestriction);

    List<Book> getAllGoldenEditionBooksWithCopiesLessThan5000();

    List<Book> getAllByPriceLowerThan5AndGreaterThan40();

    List<Book> getAllAreNotReleasedThisYear(String year);

    String booksContainingIgnoreCase(String sequence);

    String getBooksReleasedBefore(String date);
}

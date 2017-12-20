package book.library.java.dto;

import book.library.java.model.Author;
import book.library.java.model.Book;

/**
 * Represent a BookWithAuthors. This class for convenient transfer of information between front-end and back-end.
 * BookWithAuthors have book(model) and list of authors(author model)
 */
import java.util.List;

public class BookWithAuthors {
    Book book; // todo: why not private?
    List<Author> authors; // todo: why not private?

    public BookWithAuthors() {
    }

    public BookWithAuthors(Book book, List<Author> authors) {
        this.book = book;
        this.authors = authors;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}

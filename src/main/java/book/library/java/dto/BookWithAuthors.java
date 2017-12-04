package book.library.java.dto;

import book.library.java.model.Author;
import book.library.java.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookWithAuthors {
    Book book;
    List<Author> authors;

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

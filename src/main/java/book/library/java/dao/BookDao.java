package book.library.java.dao;

import book.library.java.model.Book;

import java.util.List;

public interface BookDao extends InterfaceDao<Book> {
    List<Book> getByAuthor(String authorId);
    List<Book> getByRating(Double rating);
}

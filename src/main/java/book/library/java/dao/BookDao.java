package book.library.java.dao;

import book.library.java.model.Book;

import java.util.List;

public interface BookDao extends AbstractDao<Book> {
    Integer countBooksByAuthorId(Integer authorId);
    List<Book> getByAuthor(Integer authorId);
    List<Book> getByRating(Double rating);
}

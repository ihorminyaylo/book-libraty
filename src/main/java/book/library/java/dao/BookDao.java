package book.library.java.dao;

import book.library.java.dto.BookWithAuthors;
import book.library.java.exception.DaoException;
import book.library.java.model.Book;

import java.util.List;

public interface BookDao extends AbstractDao<Book> {
    void create(BookWithAuthors bookWithAuthors) throws DaoException;

    Integer countBooksByAuthorId(Integer authorId);

    List<Book> getByAuthor(Integer authorId);

    List<Book> getByRating(Double rating);
}

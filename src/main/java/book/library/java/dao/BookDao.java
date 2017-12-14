package book.library.java.dao;

import book.library.java.dto.BookWithAuthors;
import book.library.java.exception.DaoException;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;

import java.math.BigDecimal;
import java.util.List;

public interface BookDao extends AbstractDao<Book, BookPattern> {
    Integer create(BookWithAuthors bookWithAuthors) throws DaoException;

    List<Book> readTop(Integer count);

    BigDecimal getAverageRating() throws DaoException;
}

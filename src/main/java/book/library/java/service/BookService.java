package book.library.java.service;

import book.library.java.dto.BookWithAuthors;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;

import java.math.BigDecimal;
import java.util.List;

// todo: Please add java-doc for class and all methods
public interface BookService extends AbstractService<Book, BookPattern> {
    Integer create(BookWithAuthors bookWithAuthors) throws BusinessException;

    List<Book> readTop(Integer count);

    BigDecimal getAverageRating() throws DaoException;

    void bulkDelete(List<Integer> idEntities) throws BusinessException;
}

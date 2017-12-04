package book.library.java.service;

import book.library.java.dto.BookWithAuthors;
import book.library.java.exception.BusinessException;
import book.library.java.model.Book;

public interface BookService extends AbstractService<Book> {
    void create(BookWithAuthors bookWithAuthors) throws BusinessException;
}

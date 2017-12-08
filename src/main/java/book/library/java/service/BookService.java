package book.library.java.service;

import book.library.java.dto.BookWithAuthors;
import book.library.java.exception.BusinessException;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;

import java.util.List;

public interface BookService extends AbstractService<Book, BookPattern> {
    void create(BookWithAuthors bookWithAuthors) throws BusinessException;
    void bulkDelete(List<Integer> idEntities) throws BusinessException;
}

package book.library.java.service;

import book.library.java.exception.BusinessException;
import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;

import java.util.List;

public interface AuthorService extends AbstractService<Author, AuthorPattern> {
    List<Author> readAll();
    List<Author> readByBook(Integer idBook) throws BusinessException;
    List<Author> bulkDelete(List<Integer> idEntities) throws BusinessException;
}

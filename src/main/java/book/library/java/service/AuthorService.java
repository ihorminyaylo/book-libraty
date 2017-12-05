package book.library.java.service;

import book.library.java.exception.BusinessException;
import book.library.java.model.Author;
import book.library.java.service.AbstractService;

import java.util.List;

public interface AuthorService extends AbstractService<Author> {
    List<Author> readByBook(Integer idBook) throws BusinessException;
}

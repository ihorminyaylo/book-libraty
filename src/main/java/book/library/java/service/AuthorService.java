package book.library.java.service;

import book.library.java.dto.AuthorDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.Book;
import book.library.java.model.pattern.AuthorPattern;

import java.util.List;

// todo: Please add java-doc for class and all methods
public interface AuthorService extends AbstractService<Author, AuthorPattern> {
    List<Author> readAll();

    List<AuthorDto> readTop(Integer count) throws DaoException;

    List<AuthorDto> bulkDelete(List<Integer> idEntities) throws BusinessException, DaoException;

    AuthorDto deleteAuthor(Integer idAuthor) throws BusinessException;
}

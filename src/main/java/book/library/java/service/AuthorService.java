package book.library.java.service;

import book.library.java.dto.AuthorDto;
import book.library.java.exception.BusinessException;
import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;

import java.util.List;

public interface AuthorService extends AbstractService<Author, AuthorPattern> {
    List<Author> readAll();

    List<AuthorDto> bulkDelete(List<Integer> idEntities) throws BusinessException;

    AuthorDto deleteAuthor(Integer idAuthor) throws BusinessException;
}

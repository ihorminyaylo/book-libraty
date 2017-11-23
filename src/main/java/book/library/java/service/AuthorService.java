package book.library.java.service;

import book.library.java.dto.AuthorsAndPageDto;
import book.library.java.dto.AuthorDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;


import java.util.List;
import java.util.Map;

public interface AuthorService {
    void create(AuthorDto authorDto) throws DaoException;
    List<AuthorDto> read(Map<String, String> params);
    void update(AuthorDto authorDto) throws DaoException;
    void delete(Integer idAuthorDto) throws DaoException;

    AuthorsAndPageDto read(Integer page, Integer pageSize) throws BusinessException;
}

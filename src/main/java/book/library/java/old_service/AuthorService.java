package book.library.java.old_service;

import book.library.java.dto.ReadParamsDto;
import book.library.java.dto.AuthorsAndPageDto;
import book.library.java.dto.AuthorDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;

public interface AuthorService {
    void create(Author authorDto) throws BusinessException;
    AuthorsAndPageDto read(ReadParamsDto readParamsDto) throws BusinessException;
    void update(AuthorDto authorDto) throws DaoException;
    void delete(Integer idAuthorDto) throws DaoException;





    //todo: remove

/*    List<AuthorDto> readOld(Map<String, String> params);
    AuthorsAndPageDto readOld(Integer page, Integer pageSize) throws BusinessException;*/
}

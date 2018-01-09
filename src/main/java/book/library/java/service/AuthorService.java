package book.library.java.service;

import book.library.java.dto.AuthorDto;
import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.list.ListParams;
import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;

import java.util.List;


/**
 * Represent a Author BaseService
 * which extends of BaseService with generic Author(type of entity) and AuthorPattern(type of Pattern for definite entity)
 * A Author BaseService have such methods: readAll, readTop, bulkDelete, deleteAuthor
 */
public interface AuthorService extends BaseService<Author, AuthorPattern, AuthorDto> {

    /**
     * This method for get entities with params and get total items these entities.
     *
     * @param listParams with condition type
     * @return ListEntityPage with condition type
     * @throws BusinessException
     */
    ListEntityPage<AuthorDto> readAuthor(ListParams<AuthorPattern> listParams) throws BusinessException;

    /**
     * This method for read top authors.
     *
     * @return List authors DTO
     * @throws BusinessException
     */
    List<AuthorDto> readTopFive() throws BusinessException;
}

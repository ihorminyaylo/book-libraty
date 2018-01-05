package book.library.java.service;

import book.library.java.dto.AuthorDto;
import book.library.java.exception.BusinessException;
import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;

import java.util.List;


/**
 * Represent a Author BaseService
 * which extends of BaseService with generic Author(type of entity) and AuthorPattern(type of Pattern for definite entity)
 * A Author BaseService have such methods: readAll, readTop, bulkDelete, deleteAuthor
 */
public interface AuthorService extends BaseService<Author, AuthorPattern> {

    /**
     * This method for get all authors.
     *
     * @return List authors
     */
    List<Author> readAll();

    /**
     * This method for read top authors.
     *
     * @return List authors DTO
     * @throws BusinessException
     */
    List<AuthorDto> readTopFive() throws BusinessException;

    AuthorDto readById(Integer idAuthor) throws BusinessException;

    /**
     * This method for bulk delete authors
     *
     * @param idEntities is List of id authors which we want remove
     * @return List author DTO which we can't remove
     * @throws BusinessException
     */
    List<Integer> bulkDelete(List<Integer> idEntities) throws BusinessException;

    /**
     * This method for delete one author by id
     *
     * @param idAuthor is id of author which we want to remove
     * @return Author DTO which we can't remove
     * @throws BusinessException
     */
    Integer deleteAuthor(Integer idAuthor) throws BusinessException;
}

package book.library.java.service;

import book.library.java.dto.AuthorDto;
import book.library.java.exception.BusinessException;
import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;

import java.util.List;


/**
 * Represent a Author Service
 * which extends of AbstractService with generic Author(type of entity) and AuthorPattern(type of Pattern for definite entity)
 * A Author Service have such methods: readAll, readTop, bulkDelete, deleteAuthor
 */
public interface AuthorService extends AbstractService<Author, AuthorPattern> {
    /**
     * This method for get all authors.
     *
     * @return List authors
     */
    List<Author> readAll();

    /**
     * This method for read top authors. Count of top we pass like argument in method.
     *
     * @param count of we want to get top authors
     * @return List authors DTO
     * @throws BusinessException
     */
    List<AuthorDto> readTop(Integer count) throws BusinessException;

    /**
     * This method for bulk delete authors
     *
     * @param idEntities is List of id authors which we want remove
     * @return List author DTO which we can't remove
     * @throws BusinessException
     */
    List<AuthorDto> bulkDelete(List<Integer> idEntities) throws BusinessException;

    /**
     * This method for delete one author by id
     *
     * @param idAuthor is id of author which we want to remove
     * @return Author DTO which we can't remove
     * @throws BusinessException
     */
    AuthorDto deleteAuthor(Integer idAuthor) throws BusinessException;
}

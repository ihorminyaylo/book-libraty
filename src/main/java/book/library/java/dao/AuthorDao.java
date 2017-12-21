package book.library.java.dao;

import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;

import java.util.List;

/**
 * Represent a Author DAO with generic Author(type of entity) and AuthorPattern(type of Pattern)
 * A Author DAO have next methods: readTop, deleteAuthor, bulkDelete
 */
public interface AuthorDao extends AbstractDao<Author, AuthorPattern> {
    /**
     * This method for get top authors from Data Base. Count of top we pass like argument in method.
     * @return List of authors
     * @throws DaoException
     */
    List<Author> readTopFive() throws DaoException;

    /**
     * This method for delete author by id
     * @param idAuthor
     * @return Author which we can't deleted, because he has books
     * @throws DaoException
     */
    Author deleteAuthor(Integer idAuthor) throws DaoException;

    /**
     * This method for bulk delete authors by id
     * @param idEntities
     * @return List authors which we can't deleted, because they have books
     * @throws DaoException
     */
    List<Author> bulkDelete(List<Integer> idEntities) throws DaoException;
}

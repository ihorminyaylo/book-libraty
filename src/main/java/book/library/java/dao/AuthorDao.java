package book.library.java.dao;

import book.library.java.exception.DaoException;
import book.library.java.model.Author;

import java.util.List;

public interface AuthorDao extends AbstractDao<Author> {
    List<Author> bulkDeleteAuthors(List<Integer> idEntities) throws DaoException;
    //todo: not work
    List<Author> getByAverageRating();
}

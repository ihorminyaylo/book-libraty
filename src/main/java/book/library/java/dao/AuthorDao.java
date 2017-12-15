package book.library.java.dao;

import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;

import java.util.List;

public interface AuthorDao extends AbstractDao<Author, AuthorPattern> {
    List<Author> readTop(Integer count) throws DaoException;
    Author deleteAuthor(Integer idAuthor) throws DaoException;

    List<Author> bulkDeleteAuthors(List<Integer> idEntities) throws DaoException;
}

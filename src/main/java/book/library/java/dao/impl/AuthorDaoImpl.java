package book.library.java.dao.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorDaoImpl extends AbstractDao<Author, AuthorPattern> implements AuthorDao {


    @Override
    public Integer delete(Integer idAuthor) throws DaoException {
        Integer countBook = Integer.parseInt(
            entityManager.createNativeQuery("SELECT count(*) FROM author_book WHERE author_id = :authorId")
                .setParameter("authorId", idAuthor).getSingleResult().toString());
        if (countBook != 0) {
            return idAuthor;
        }
        try {
            super.delete(idAuthor);
            return null;
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e.getCause());
        }
    }
}

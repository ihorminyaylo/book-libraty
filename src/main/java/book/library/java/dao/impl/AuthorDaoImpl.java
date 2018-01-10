package book.library.java.dao.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorDaoImpl extends AbstractDao<Author, AuthorPattern> implements AuthorDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorDaoImpl.class);

    @Override
    public Integer delete(Integer idAuthor) throws DaoException {
        Integer countBook = ((Number)
            entityManager.createNativeQuery("SELECT count(*) FROM author_book WHERE author_id = :authorId")
                .setParameter("authorId", idAuthor)
                .getSingleResult()).intValue();
        if (countBook != 0) {
            return idAuthor;
        }
        try {
            return super.delete(idAuthor);
        } catch (Exception e) {
            LOGGER.error("in delete(id) exception!", e);
            throw new DaoException(e);
        }
    }
}

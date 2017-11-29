package book.library.java.dao.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorDaoImpl extends AbstractDaoImpl<Author> implements AuthorDao {

    @Override
    public List<Author> delete(List<Integer> idEntities) throws DaoException {
        List<Author> notRemove = new ArrayList<>();
        for (Integer entityId : idEntities) {
            if (entityId == null) {
                throw new DaoException("Entity id can't be null");
            }
            Author entity = get(entityId);
            try {
                entityManager.remove(entity);
            } catch (Exception e) {
                notRemove.add(entity);
            }
        }
        return notRemove;
    }








    //todo: not work
    @Override
    public List<Author> getByAverageRating() {
        StringBuilder query = new StringBuilder("SELECT * FROM authors INNER JOIN author_book_keys ON authors.id = author_book_keys.author_id INNER JOIN reviews ON author_book_keys.book_id = reviews.book_id");
        return entityManager.createNativeQuery(query.toString(), Author.class).getResultList();
    }
}

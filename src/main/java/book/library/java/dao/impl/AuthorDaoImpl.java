package book.library.java.dao.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorDaoImpl extends AbstractDaoImpl<Author> implements AuthorDao {

    @Override
    public List<Author> bulkDeleteAuthors(List<Integer> idEntities) throws DaoException {
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

    @Override
    public List<Author> readByBook(Integer idBook) throws DaoException {
        List<Author> list = entityManager.createNativeQuery("SELECT id, first_name, second_name, create_date, average_rating FROM author as a JOIN author_book as ab ON a.id = ab.author_id WHERE ab.book_id = :idBook").setParameter("idBook", idBook).getResultList();
        return list;
    }


    //todo: not work
    @Override
    public List<Author> getByAverageRating() {
        StringBuilder query = new StringBuilder("SELECT * FROM authors INNER JOIN author_book_keys ON authors.id = author_book_keys.author_id INNER JOIN reviews ON author_book_keys.book_id = reviews.book_id");
        return entityManager.createNativeQuery(query.toString(), Author.class).getResultList();
    }
}

package book.library.java.dao.impl;

import book.library.java.dao.AbstractDao;
import book.library.java.dao.ReviewDao;
import book.library.java.model.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl extends AbstractDao<Review> implements ReviewDao {
    @Override
    public List<Review> getByBookId(String id) {
        StringBuilder query = new StringBuilder("FROM Review r WHERE r.book.id = ");
        query.append(id);
        return entityManager.createNamedQuery(query.toString(), Review.class).getResultList();
    }
}

package book.library.java.dao.impl;

import book.library.java.dao.ReviewDao;
import book.library.java.model.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl extends AbstractDaoImpl<Review> implements ReviewDao {
    @Override
    public List<Review> getByBookId(Integer id) {
        StringBuilder query = new StringBuilder("FROM Review r WHERE r.book.id = ");
        query.append(id);
        return entityManager.createNamedQuery(query.toString(), Review.class).getResultList();
    }

    @Override
    public List<Review> getReviewByAuthorId(Integer id) {
        StringBuilder query = new StringBuilder("SELECT * FROM reviews JOIN author_book_keys ON reviews.book_id = author_book_keys.book_id WHERE author_id = '");
        query.append(id).append("'");
        return entityManager.createNativeQuery(query.toString(), Review.class).getResultList();
    }
}

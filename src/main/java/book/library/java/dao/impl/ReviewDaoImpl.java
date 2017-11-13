package book.library.java.dao.impl;

import book.library.java.dao.AbstractDao;
import book.library.java.dao.ReviewDao;
import book.library.java.model.Author;
import book.library.java.model.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.PostPersist;
import java.util.List;

@Repository
public class ReviewDaoImpl extends AbstractDao<Review> implements ReviewDao {
    @Override
    public List<Review> getByBookId(String id) {
        StringBuilder query = new StringBuilder("FROM Review r WHERE r.book.id = ");
        query.append(id);
        return entityManager.createNamedQuery(query.toString(), Review.class).getResultList();
    }

    @Override
    public List<Review> getReviewByAuthorId(String id) {
        StringBuilder query = new StringBuilder("SELECT * FROM reviews JOIN author_book_keys ON reviews.book_id = author_book_keys.book_id WHERE author_id = '");
        query.append(id).append("'");
        return entityManager.createNativeQuery(query.toString(), Review.class).getResultList();
    }

    @Override
    public void add(Review entity) {
        entityManager.persist(entity);
    }

    @Override
    public void set(Review entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(String entityId) {
        Review entity = get(entityId);
        entityManager.remove(entity);
    }

    @PostPersist
    public void process(Author author) {
        List<Review> reviewList = getReviewByAuthorId(author.getId());
        Double averageRatingByAuthor = reviewList.stream().mapToDouble(Review::getRating).average().getAsDouble();
        author.setAverageRating(averageRatingByAuthor);
    }
}

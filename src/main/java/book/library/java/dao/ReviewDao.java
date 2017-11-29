package book.library.java.dao;

import book.library.java.model.Review;

import java.util.List;

public interface ReviewDao extends AbstractDao<Review> {
    List<Review> getByBookId(Integer id);
    List<Review> getReviewByAuthorId(Integer id);
}

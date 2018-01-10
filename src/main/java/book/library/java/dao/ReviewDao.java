package book.library.java.dao;

import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;

/**
 * Represent a Review DAO with generic Review(type of entity) and ReviewPattern(type of Pattern)
 */
public interface ReviewDao extends Dao<Review, ReviewPattern> {
}

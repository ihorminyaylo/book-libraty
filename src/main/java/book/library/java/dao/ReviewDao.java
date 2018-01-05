package book.library.java.dao;

import book.library.java.dto.ReviewPageDto;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;

import java.util.List;

/**
 * Represent a Review DAO with generic Review(type of entity) and ReviewPattern(type of Pattern)
 */
public interface ReviewDao extends Dao<Review, ReviewPattern> {
}

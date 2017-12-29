package book.library.java.dao;

import book.library.java.dto.ReviewPageDto;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;

import java.util.List;

/**
 * Represent a Review DAO with generic Review(type of entity) and ReviewPattern(type of Pattern)
 * A Review DAO have next methods: create, readTop, bulkDelete
 */
public interface ReviewDao extends Dao<Review, ReviewPattern> {
    /**
     * This method for get ReviewPageDto with fields rating(stars) - unique and count of each rating
     *
     * @return List of ReviewPageDto
     */
    List<ReviewPageDto> getCountOfEachRating();
}

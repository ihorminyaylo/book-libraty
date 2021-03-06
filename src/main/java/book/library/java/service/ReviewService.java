package book.library.java.service;

import book.library.java.dto.ListEntityPage;
import book.library.java.dto.ReviewDto;
import book.library.java.exception.BusinessException;
import book.library.java.list.ListParams;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;

/**
 * Represent a Review BaseService
 * which extends of BaseService with generic Review(type of entity) and ReviewPattern(type of Pattern for definite entity)
 * A Review BaseService have such methods: getCountOfEachRating, readReviews
 */
public interface ReviewService extends BaseService<Review, ReviewPattern, ReviewDto> {

    /**
     * This method for get review by definite parameters.
     *
     * @param listParams params foe search
     * @return Review DTO
     * @throws BusinessException on error
     */
    ListEntityPage<ReviewDto> readReviews(ListParams<ReviewPattern> listParams) throws BusinessException;
}

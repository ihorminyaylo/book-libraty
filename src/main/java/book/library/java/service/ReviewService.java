package book.library.java.service;

import book.library.java.dto.ListEntityPage;
import book.library.java.dto.ReviewDto;
import book.library.java.dto.ReviewPageDto;
import book.library.java.exception.BusinessException;
import book.library.java.list.ListParams;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;

import java.util.List;

/**
 * Represent a Review Service
 * which extends of AbstractService with generic Review(type of entity) and ReviewPattern(type of Pattern for definite entity)
 * A Review Service have such methods: getCountOfEachRating, readReviews
 */
public interface ReviewService extends AbstractService<Review, ReviewPattern> {

    /**
     * This method for get all types rating(stars) and count book of this rating
     * @return List of Page DTO where definite next fields: rating and count
     */
    List<ReviewPageDto> getCountOfEachRating();

    /**
     * This method for get review by definite parameters.
     * @param listParams
     * @return Review DTO
     * @throws BusinessException
     */
    ListEntityPage<ReviewDto> readReviews(ListParams<ReviewPattern> listParams) throws BusinessException;
}

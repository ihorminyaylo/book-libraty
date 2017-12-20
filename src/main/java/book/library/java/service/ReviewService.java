package book.library.java.service;

import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.dto.ReviewDto;
import book.library.java.dto.ReviewPageDto;
import book.library.java.exception.DaoException;
import book.library.java.model.ListParams;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;

import java.util.List;

// todo: Please add java-doc for class and all methods
public interface ReviewService extends AbstractService<Review, ReviewPattern> {
    List<ReviewPageDto> readDetail();
    EntitiesAndPageDto<ReviewDto> readReviews(ListParams listParams) throws DaoException;
}

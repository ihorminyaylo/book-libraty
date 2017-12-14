package book.library.java.dao;

import book.library.java.dto.ReviewPageDto;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;

import java.util.List;

public interface ReviewDao extends AbstractDao<Review, ReviewPattern> {
    List<ReviewPageDto> getCountOfTypeReview();
}

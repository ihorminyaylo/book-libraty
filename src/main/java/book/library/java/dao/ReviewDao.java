package book.library.java.dao;

import book.library.java.dto.ReviewPageDto;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;

import java.util.List;

// todo: Please add java-doc for class and all methods
public interface ReviewDao extends AbstractDao<Review, ReviewPattern> {
    List<ReviewPageDto> getCountOfTypeReview();
}

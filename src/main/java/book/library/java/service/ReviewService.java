package book.library.java.service;

import book.library.java.dto.ReviewPageDto;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;

import java.util.List;

public interface ReviewService extends AbstractService<Review, ReviewPattern> {
    List<ReviewPageDto> readDetail();
}

package book.library.java.service;

import book.library.java.dto.ReviewPageDto;
import book.library.java.model.Review;

import java.util.List;
import java.util.Map;

public interface ReviewService extends AbstractService<Review> {
    List<ReviewPageDto> readDetail();
}

package book.library.java.service;

import book.library.java.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    void create(ReviewDto reviewDto);
    List<ReviewDto> read();
    void update(ReviewDto reviewDto);
    void delete(String idReviewDto);
}

package book.library.java.service;

import book.library.java.dto.ReviewDto;
import book.library.java.exception.DaoException;

import java.util.List;

public interface ReviewService {
    void create(ReviewDto reviewDto) throws DaoException;
    List<ReviewDto> read();
    void update(ReviewDto reviewDto) throws DaoException;
    void delete(Integer idReviewDto) throws DaoException;
}

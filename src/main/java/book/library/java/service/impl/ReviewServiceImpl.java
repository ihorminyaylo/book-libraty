package book.library.java.service.impl;

import book.library.java.dao.ReviewDao;
import book.library.java.dto.ReviewDto;
import book.library.java.mapper.ReviewMapper;
import book.library.java.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public void create(ReviewDto reviewDto) {
        reviewDao.add(ReviewMapper.MAPPER.fromDto(reviewDto));
    }

    @Override
    public List<ReviewDto> read() {
        return reviewDao.find().stream().map(ReviewMapper.MAPPER :: toDto).collect(Collectors.toList());
    }

    @Override
    public void update(ReviewDto reviewDto) {
        reviewDao.set(ReviewMapper.MAPPER.fromDto(reviewDto));
    }

    @Override
    public void delete(String idReviewDto) {
        reviewDao.delete(idReviewDto);
    }
}

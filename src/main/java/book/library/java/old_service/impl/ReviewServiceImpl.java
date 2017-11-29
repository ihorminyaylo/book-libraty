package book.library.java.old_service.impl;

import book.library.java.dao.ReviewDao;
import book.library.java.dto.ReviewDto;
import book.library.java.exception.DaoException;
import book.library.java.mapper.ReviewMapper;
import book.library.java.old_service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Transactional
    @Override
    public void create(ReviewDto reviewDto) throws DaoException {
        reviewDao.create(ReviewMapper.MAPPER.fromDto(reviewDto));
    }

    @Override
    public List<ReviewDto> read() {
        return reviewDao.findAll().stream().map(ReviewMapper.MAPPER :: toDto).collect(Collectors.toList());
    }

    @Override
    public void update(ReviewDto reviewDto) throws DaoException {
        reviewDao.update(ReviewMapper.MAPPER.fromDto(reviewDto));
    }

    @Override
    public void delete(Integer idReviewDto) throws DaoException {
        //reviewDao.delete(idReviewDto);
    }
}

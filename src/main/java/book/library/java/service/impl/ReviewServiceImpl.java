package book.library.java.service.impl;

import book.library.java.dao.ReviewDao;
import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.dto.ReviewDto;
import book.library.java.dto.ReviewPageDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Book;
import book.library.java.model.ListParams;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;
import book.library.java.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl extends AbstractServiceImpl<Review, ReviewPattern> implements ReviewService {
    private final ReviewDao reviewDao; // todo: useless - remove

    @Autowired
    // todo: why: @Qualifier("reviewDaoImpl") AbstractDaoImpl<Review, ReviewPattern> entityDaoType ??
    public ReviewServiceImpl(@Qualifier("reviewDaoImpl") AbstractDaoImpl<Review, ReviewPattern> entityDaoType) {
        super(entityDaoType);
        reviewDao = (ReviewDao) entityDaoType;
    }

    @Override
    public Integer create(Review review) throws BusinessException, DaoException { // todo: why DaoException in signature?
        validateReview(review);
        return super.create(review);
    }

    @Override
    public EntitiesAndPageDto<ReviewDto> readReviews(ListParams listParams) throws DaoException { // todo: generic for ListParams!, why DaoException in signature?
        List<Review> listEntity;
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        Integer totalItems = reviewDao.totalRecords(listParams);
	    // todo: very strange condition and behaviour ???
        if (listParams.getLimit() == null || listParams.getOffset() != null) {
            listEntity = reviewDao.find(listParams);
        } else {
            listEntity = reviewDao.findAll();
        }
	    // todo: stream + map
        listEntity.forEach(review -> reviewDtoList.add(new ReviewDto(review.getId(), review.getCommenterName(), review.getComment(), review.getRating(), review.getCreateDate().toString())));
/*
        listEntity.forEach(review -> review.getBook().getAuthors().size());
*/
        return new EntitiesAndPageDto<>(reviewDtoList, totalItems);
    }

    @Override
    public List<ReviewPageDto> readDetail() { // todo: this method does not have sense
        return reviewDao.getCountOfTypeReview();
    }

    @Override
    public void update(Review review) throws BusinessException, DaoException { // todo: why DaoException in signature?
        validateReview(review);
        super.update(review);
    }

    void validateReview(Review review) throws BusinessException {
        if (review.getComment() == null || review.getComment().isEmpty()) {
            throw new BusinessException("Comment of book isn't correct");
        }
        if (review.getCommenterName() == null || review.getCommenterName().isEmpty()) {
            throw new BusinessException("Comment name of book isn't correct");
        }
        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            throw new BusinessException("Comment name of book isn't correct");
        }
    }
}

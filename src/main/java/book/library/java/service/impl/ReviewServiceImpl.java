package book.library.java.service.impl;

import book.library.java.dao.ReviewDao;
import book.library.java.dto.ListEntityPage;
import book.library.java.dto.ReviewDto;
import book.library.java.dto.ReviewPageDto;
import book.library.java.exception.BusinessException;
import book.library.java.list.ListParams;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;
import book.library.java.service.ReviewService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl extends AbstractService<Review, ReviewPattern> implements ReviewService {

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        super(reviewDao);
    }

    @Override
    public ListEntityPage<ReviewDto> readReviews(ListParams<ReviewPattern> listParams) throws BusinessException {
        List<Review> reviewList;
        Integer totalItems = getDao().totalRecords(listParams);
        if (listParams.getLimit() != null && listParams.getOffset() != null) {
            try {
                reviewList = getDao().find(listParams);
            } catch (Exception e) {
                throw new BusinessException(e);
            }
        } else {
            reviewList = getDao().findAll();
        }
        List<ReviewDto> reviewDtoList = reviewList.stream().map(ReviewDto::new).collect(Collectors.toList());
        return new ListEntityPage<>(reviewDtoList, totalItems);
    }

    @Override
    public List<ReviewPageDto> getCountOfEachRating() {
        return ((ReviewDao) getDao()).getCountOfEachRating();
    }

    @Override
    public void update(Review review) throws BusinessException {
        validateEntity(review);
        super.update(review);
    }

    @Override
    public void validateEntity(Review review) throws BusinessException {
        if (StringUtils.isBlank(review.getComment())) {
            throw new BusinessException("Comment of book isn't correct");
        }
        if (StringUtils.isBlank(review.getCommenterName())) {
            throw new BusinessException("Comment name of book isn't correct");
        }
        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            throw new BusinessException("Comment name of book isn't correct");
        }
    }
}

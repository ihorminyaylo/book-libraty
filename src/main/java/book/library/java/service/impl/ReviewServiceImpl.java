package book.library.java.service.impl;

import book.library.java.dao.ReviewDao;
import book.library.java.dto.ListEntityPage;
import book.library.java.dto.ReviewDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.list.ListParams;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;
import book.library.java.service.ReviewService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl extends AbstractService<ReviewDao, Review, ReviewPattern, ReviewDto> implements ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        super(reviewDao);
    }

    @Override
    public ListEntityPage<ReviewDto> readReviews(ListParams<ReviewPattern> listParams) throws BusinessException {
        List<Review> reviewList;
        Integer totalItems;
        try {
            totalItems = getDao().totalRecords(listParams);
        } catch (DaoException e) {
            LOGGER.error("in readReviews() exception!", e);
            throw new BusinessException(e);
        }
        if (listParams.getLimit() != null && listParams.getOffset() != null) {
            try {
                reviewList = getDao().find(listParams);
            } catch (Exception e) {
                LOGGER.error("in readReviews() exception!", e);
                throw new BusinessException(e);
            }
        } else {
            reviewList = getDao().findAll();
        }
        List<ReviewDto> reviewDtoList = reviewList.stream().map(ReviewDto::new).collect(Collectors.toList());
        return new ListEntityPage<>(reviewDtoList, totalItems);
    }

    @Override
    public void validateEntity(Review review) throws BusinessException {
        if (StringUtils.isBlank(review.getComment())) {
            LOGGER.error("in validateEntity() exception!");
            throw new BusinessException("Comment of book isn't correct");
        }
        if (StringUtils.isBlank(review.getCommenterName())) {
            LOGGER.error("in validateEntity() exception!");
            throw new BusinessException("Comment name of book isn't correct");
        }
        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            LOGGER.error("in validateEntity() exception!");
            throw new BusinessException("Comment name of book isn't correct");
        }
    }
}

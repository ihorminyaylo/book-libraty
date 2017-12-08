package book.library.java.service.impl;

import book.library.java.dao.ReviewDao;
import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dto.ReviewPageDto;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;
import book.library.java.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl extends AbstractServiceImpl<Review, ReviewPattern> implements ReviewService {
    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(@Qualifier("reviewDaoImpl") AbstractDaoImpl<Review, ReviewPattern> entityDaoType) {
        super(entityDaoType);
        reviewDao = (ReviewDao) entityDaoType;
    }

    @Override
    public List<ReviewPageDto> readDetail() {
        return reviewDao.getCountOfTypeReview();
    }
}

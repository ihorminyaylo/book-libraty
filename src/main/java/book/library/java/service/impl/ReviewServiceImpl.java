package book.library.java.service.impl;

import book.library.java.dao.BookDao;
import book.library.java.dao.ReviewDao;
import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.dto.ReadParamsDto;
import book.library.java.dto.ReviewPageDto;
import book.library.java.exception.BusinessException;
import book.library.java.model.Book;
import book.library.java.model.Review;
import book.library.java.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class ReviewServiceImpl extends AbstractServiceImpl<Review> implements ReviewService {
    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(@Qualifier("reviewDaoImpl") AbstractDaoImpl<Review> entityDaoType) {
        super(entityDaoType);
        reviewDao = (ReviewDao) entityDaoType;
    }

    @Override
    public List<ReviewPageDto> readDetail() {
        return reviewDao.getCountOfTypeReview();
    }
}

package book.library.java.service.impl;

import book.library.java.dao.BookDao;
import book.library.java.dao.ReviewDao;
import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.model.Book;
import book.library.java.model.Review;
import book.library.java.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewServiceImpl extends AbstractServiceImpl<Review> implements ReviewService {
    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(@Qualifier("bookDaoImpl") AbstractDaoImpl<Review> entityDaoType) {
        super(entityDaoType);
        reviewDao = (ReviewDao) entityDaoType;
    }
}

package book.library.java.dao.impl;

import book.library.java.dao.AbstractDao;
import book.library.java.dao.ReviewDao;
import book.library.java.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDaoImpl extends AbstractDao<Review> implements ReviewDao {
}

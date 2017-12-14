package book.library.java.dao.impl;

import book.library.java.dao.ReviewDao;
import book.library.java.dto.ReviewPageDto;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl extends AbstractDaoImpl<Review, ReviewPattern> implements ReviewDao {
    @Override
    public List<ReviewPageDto> getCountOfTypeReview() {
        List<ReviewPageDto> reviewPageDtoList = (List<ReviewPageDto>) entityManager.createNativeQuery("SELECT average_rating as rating, count(average_rating) FROM book GROUP BY average_rating ORDER BY average_rating").getResultList();
        System.out.println(reviewPageDtoList);
        return reviewPageDtoList;
    }
}

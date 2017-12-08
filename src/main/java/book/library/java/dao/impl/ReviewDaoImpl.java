package book.library.java.dao.impl;

import book.library.java.dao.ReviewDao;
import book.library.java.dto.ReviewPageDto;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;
import org.springframework.stereotype.Repository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;

@Repository
@SqlResultSetMapping(
    name = "ReviewPageDto",
    classes = @ConstructorResult(
        targetClass = ReviewPageDto.class,
        columns = {
            @ColumnResult(name = "rating"),
            @ColumnResult(name = "count")}))
public class ReviewDaoImpl extends AbstractDaoImpl<Review, ReviewPattern> implements ReviewDao {
    @Override
    public List<ReviewPageDto> getCountOfTypeReview() {
        List<ReviewPageDto> reviewPageDtoList = (List<ReviewPageDto>) entityManager.createNativeQuery("SELECT average_rating as rating, count(average_rating) FROM book GROUP BY average_rating ORDER BY average_rating").getResultList();
        System.out.println(reviewPageDtoList);
        return reviewPageDtoList;
    }

    @Override
    public List<Review> getByBookId(Integer id) {
        StringBuilder query = new StringBuilder("FROM Review r WHERE r.book.id = ");
        query.append(id);
        return entityManager.createNamedQuery(query.toString(), Review.class).getResultList();
    }

    @Override
    public List<Review> getReviewByAuthorId(Integer id) {
        StringBuilder query = new StringBuilder("SELECT * FROM reviews JOIN author_book_keys ON reviews.book_id = author_book_keys.book_id WHERE author_id = '");
        query.append(id).append("'");
        return entityManager.createNativeQuery(query.toString(), Review.class).getResultList();
    }
}

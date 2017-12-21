package book.library.java.dao.impl;

import book.library.java.dao.ReviewDao;
import book.library.java.dto.ReviewPageDto;
import book.library.java.exception.DaoException;
import book.library.java.list.ListParams;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl extends AbstractDaoImpl<Review, ReviewPattern> implements ReviewDao {

    @Override
    public List<Review> find(ListParams<ReviewPattern> listParams) throws DaoException {
        StringBuilder query = new StringBuilder("SELECT * FROM  review");
        query = generateQueryWithParams(listParams, query);
        Query nativeQuery = (Query) entityManager.createNativeQuery(query.toString(), Review.class);
        nativeQuery = setParameters(listParams, nativeQuery);
        return nativeQuery.getResultList();
    }

    @Override
    public List<ReviewPageDto> getCountOfEachRating() {
        return (List<ReviewPageDto>) entityManager.
            createNativeQuery("SELECT ROUND(average_rating) as rating, count(average_rating) FROM book GROUP BY rating ORDER BY rating")
            .getResultList();
    }

    private StringBuilder generateQueryWithParams(ListParams<ReviewPattern> listParams, StringBuilder query) {
        ReviewPattern pattern = listParams != null ? listParams.getPattern() : null;
        if (pattern != null) {
            if (listParams.getPattern().getBookId() != null) {
                query.append(" WHERE book_id = :bookId");
            }
        }
        return query;
    }

    private Query setParameters(ListParams<ReviewPattern> listParams, Query nativeQuery) {
        ReviewPattern pattern = listParams != null ? listParams.getPattern() : null;
        if (listParams != null && listParams.getLimit() != null && listParams.getOffset() != null) {
            nativeQuery.setFirstResult(listParams.getOffset()).setMaxResults(listParams.getLimit());
        }
        if (pattern != null) {
            if (pattern.getBookId() != null) {
                nativeQuery.setParameter("bookId", pattern.getBookId());
            }
        }
        return nativeQuery;
    }
}

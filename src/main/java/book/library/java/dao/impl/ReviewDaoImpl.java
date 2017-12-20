package book.library.java.dao.impl;

import book.library.java.dao.ReviewDao;
import book.library.java.dto.ReviewPageDto;
import book.library.java.exception.DaoException;
import book.library.java.model.ListParams;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl extends AbstractDaoImpl<Review, ReviewPattern> implements ReviewDao {

    @Override
    public List<Review> find(ListParams<ReviewPattern> listParams) throws DaoException {
        StringBuilder startQuery = new StringBuilder("SELECT * FROM  review");
        StringBuilder query = new StringBuilder(generateQueryWithParams(listParams, startQuery)); // todo: for what are you create new object of StringBuilder ?
        if (listParams.getSortParams() != null && listParams.getSortParams().getParameter() != null && listParams.getSortParams().getType() != null) { // todo: why this check here?
            generateQueryWithSortParams(listParams, query);
        } else {
            query.append(" ORDER BY create_date");
        }
        Query nativeQuery = (Query) entityManager.createNativeQuery(query.toString(), Review.class);
        nativeQuery = setParameters(listParams, nativeQuery, "find");
        List<Review> reviewList = nativeQuery.getResultList(); // todo: redundant variable
        return reviewList;
    }

    @Override
    public List<ReviewPageDto> getCountOfTypeReview() { // todo: for what this method?
        return (List<ReviewPageDto>) entityManager.createNativeQuery("SELECT average_rating as rating, count(average_rating) FROM book GROUP BY average_rating ORDER BY average_rating").getResultList();
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

    private Query setParameters(ListParams<ReviewPattern> listParams, Query nativeQuery, String type) { // todo: for what "type" ?
        ReviewPattern pattern = listParams != null ? listParams.getPattern() : null;
        if ("find".equals(type)) {
            if (listParams != null && listParams.getLimit() != null && listParams.getOffset() != null) {
                nativeQuery.setFirstResult(listParams.getOffset()).setMaxResults(listParams.getLimit());
            }
        }
        if (pattern != null) {
            if (pattern.getBookId() != null) {
                nativeQuery.setParameter("bookId",pattern.getBookId());
            }
        }
        return nativeQuery;
    }
}

package book.library.java.dao.impl;

import book.library.java.dao.ReviewDao;
import book.library.java.list.ListParams;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDaoImpl extends AbstractDao<Review, ReviewPattern> implements ReviewDao {

    @Override
    public Integer totalRecords(ListParams<ReviewPattern> listParams) {
        String queryString = "SELECT Count(*) FROM review WHERE book_id = :idBook";
        return ((Number) entityManager.createNativeQuery(queryString).setParameter("idBook", listParams.getPattern().getBookId()).getSingleResult()).intValue();
    }

    @Override
    public StringBuilder generateQueryWithParams(ListParams<ReviewPattern> listParams, StringBuilder query, Boolean typeQueryFind) {
        ReviewPattern pattern = listParams != null ? listParams.getPattern() : null;
        if (pattern != null && pattern.getBookId() != null) {
            query.append(" WHERE book_id = :bookId");
        }
        return query;
    }

    @Override
    public Query setParameters(ListParams<ReviewPattern> listParams, Query nativeQuery, Boolean typeQueryFind) {
        super.setParameters(listParams, nativeQuery, typeQueryFind);
        ReviewPattern pattern = listParams != null ? listParams.getPattern() : null;
        if (pattern != null) {
            if (pattern.getBookId() != null) {
                nativeQuery.setParameter("bookId", pattern.getBookId());
            }
        }
        return nativeQuery;
    }
}

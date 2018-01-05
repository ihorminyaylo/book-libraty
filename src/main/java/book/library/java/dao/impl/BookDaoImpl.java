package book.library.java.dao.impl;

import book.library.java.dao.BookDao;
import book.library.java.dto.ReviewPageDto;
import book.library.java.list.ListParams;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl extends AbstractDao<Book, BookPattern> implements BookDao {
    @Override
    public List<ReviewPageDto> getCountOfEachRating() {
        return (List<ReviewPageDto>) entityManager.
            createNativeQuery("SELECT ROUND(average_rating) as rating, count(*) FROM book GROUP BY rating ORDER BY rating")
            .getResultList();
    }

    @Override
    public StringBuilder generateQueryWithParams(ListParams<BookPattern> listParams, StringBuilder query, Boolean typeQueryFind) {
        BookPattern pattern = listParams != null ? listParams.getPattern() : null;
        if (pattern != null) {
            if (pattern.getAuthorId() != null) {
                query.append(" JOIN author_book ON book.id = author_book.book_id");
            }
            query.append(" WHERE name ILIKE :search");
            if (pattern.getAuthorId() != null) {
                query.append(" AND author_book.author_id = :authorId");
            }
            if (pattern.getRating() != null) {
                query.append(" AND book.average_rating BETWEEN :ratingSmall AND :ratingBig");
            }
            if (typeQueryFind) {
                addSortParams(listParams, query);
            }
        } else {
            query.append(" ORDER BY average_rating, create_date");
        }
        return query;
    }

    @Override
    public Query setParameters(ListParams<BookPattern> listParams, Query nativeQuery, Boolean typeQueryFind) {
        BookPattern pattern = listParams != null ? listParams.getPattern() : null;
        super.setParameters(listParams, nativeQuery, typeQueryFind);
        if (pattern != null) {
            nativeQuery.setParameter("search", "%" + pattern.getSearch() + "%");
            if (pattern.getAuthorId() != null) {
                nativeQuery.setParameter("authorId", listParams.getPattern().getAuthorId());
            }
            if (pattern.getRating() != null) {
                nativeQuery.setParameter("ratingSmall", listParams.getPattern().getRating() - 0.5).setParameter("ratingBig", listParams.getPattern().getRating() + 0.5);
            }
        }
        return nativeQuery;
    }
}

package book.library.java.dao.impl;

import book.library.java.dao.BookDao;
import book.library.java.dto.ReviewPageDto;
import book.library.java.exception.DaoException;
import book.library.java.list.ListParams;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl extends DaoImpl<Book, BookPattern> implements BookDao {
    @SuppressWarnings("unchecked")
    @Override
    public List<ReviewPageDto> getCountOfEachRating() {
        return (List<ReviewPageDto>) entityManager.
            createNativeQuery("SELECT ROUND(average_rating) AS rating, count(*) FROM book GROUP BY rating ORDER BY rating")
            .getResultList();
    }

    @Override
    public StringBuilder createOrderWithParams(ListParams<BookPattern> listParams, StringBuilder query, Boolean typeQueryFind) throws DaoException {
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
                createOrderWithSortParams(listParams, query);
            }
        }
        return query;
    }

    @Override
    public Query addParameters(ListParams<BookPattern> listParams, Query nativeQuery, Boolean typeQueryFind) {
        BookPattern pattern = listParams != null ? listParams.getPattern() : null;
        super.addParameters(listParams, nativeQuery, typeQueryFind);
        if (pattern != null) {
            nativeQuery.setParameter("search", '%' + pattern.getSearch() + '%');
            if (pattern.getAuthorId() != null) {
                nativeQuery.setParameter("authorId", listParams.getPattern().getAuthorId());
            }
            if (pattern.getRating() != null) {
                nativeQuery.setParameter("ratingSmall", listParams.getPattern().getRating() - 0.5)
                    .setParameter("ratingBig", listParams.getPattern().getRating() + 0.5);
            }
        }
        return nativeQuery;
    }

    @Override
    public Boolean checkISBN(Book book) {
        if (book.getId() != null) {
            return (Boolean) entityManager.createNativeQuery("SELECT exists(SELECT 1 FROM book WHERE isbn = :isbn AND book.id <> :bookId)").setParameter("isbn", book.getIsbn()).setParameter("bookId", book.getId()).getSingleResult();
        } else {
            return (Boolean) entityManager.createNativeQuery("SELECT exists(SELECT 1 FROM book WHERE isbn = :isbn)").setParameter("isbn", book.getIsbn()).getSingleResult();
        }
    }
}

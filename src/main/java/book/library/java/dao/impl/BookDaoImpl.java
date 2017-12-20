package book.library.java.dao.impl;

import book.library.java.dao.BookDao;
import book.library.java.dto.BookWithAuthors;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.Book;
import book.library.java.model.ListParams;
import book.library.java.model.pattern.BookPattern;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

@Repository
public class BookDaoImpl extends AbstractDaoImpl<Book, BookPattern> implements BookDao {

    @Override
    public Integer create(BookWithAuthors bookWithAuthors) throws DaoException {
        Book book = bookWithAuthors.getBook();
        List<Author> authors = bookWithAuthors.getAuthors();
        if (bookWithAuthors == null) { // todo: why here?
            throw new DaoException("Entity can't be null");
        }
        entityManager.persist(book);
        for (Author author : authors) {  // todo: wrong implementation! Rework!
            entityManager.createNativeQuery("INSERT INTO author_book VALUES (:authorId, :bookId)")
                .setParameter("authorId", author.getId()).setParameter("bookId", book.getId())
                .executeUpdate();
        }
        return book.getId();
    }

    @Override
    public List<Book> readTop(Integer count) {
	    // todo: "ORDER BY average_rating" - is it really top books?
        List<Book> books = entityManager.createNativeQuery("SELECT * FROM book ORDER BY average_rating", Book.class).setFirstResult(0).setMaxResults(count).getResultList();
        books.forEach(book -> {
            book.getAuthors().size();  // todo: are you really need this initialization? Rework!
            book.setReviews(null);  // todo: WTF?!
        });
        return books;
    }

    @Override
    public BigDecimal getAverageRating() throws DaoException {  // todo: is this method really need?
        Double avgDouble = (Double) entityManager.createNativeQuery("SELECT AVG(average_rating) FROM book").getSingleResult();
        if (avgDouble == null) {
            throw new DaoException();
        }
        return new BigDecimal(avgDouble).round(new MathContext(2, RoundingMode.CEILING));
    }


    @Override
    public List<Book> find(ListParams<BookPattern> listParams) throws DaoException {
        StringBuilder startQuery = new StringBuilder("SELECT * FROM  book");
        StringBuilder query = new StringBuilder(generateQueryWithParams(listParams, startQuery)); // todo: for what are you create new object of StringBuilder ?
        if (listParams.getSortParams() != null && listParams.getSortParams().getParameter() != null && listParams.getSortParams().getType() != null) { // todo: why this check here?
            generateQueryWithSortParams(listParams, query);
        } else {
            query.append(" ORDER BY average_rating, create_date");
        }
        Query nativeQuery = (Query) entityManager.createNativeQuery(query.toString(), Book.class);
        nativeQuery = setParameters(listParams, nativeQuery, "find");
        List<Book> bookList = nativeQuery.getResultList();
        bookList.forEach(book -> book.getAuthors().size());
        return bookList;
    }

    public StringBuilder generateQueryWithParams(ListParams<BookPattern> listParams, StringBuilder query) {
        BookPattern pattern = listParams != null ? listParams.getPattern() : null;
        if (pattern != null) {
            if (pattern.getAuthorId() != null) {
                query.append(" JOIN author_book ON book.id = author_book.book_id");
            }
            query.append(" WHERE LOWER(name) LIKE LOWER(:search)"); // todo: remove LOWER, use another operator
            if (pattern.getAuthorId() != null) {
                query.append(" AND author_book.author_id = :authorId");
            }
            if (pattern.getRating() != null) {
                query.append(" AND ROUND(book.average_rating) = :rating"); // todo: wrong condition! Rework! rating make sense only like range
            }
        }
        return query;
    }

    public Query setParameters(ListParams<BookPattern> listParams, Query nativeQuery, String type) { // todo: for what "type" ?
        System.out.println(listParams);
        BookPattern pattern = listParams != null ? listParams.getPattern() : null;
        if ("find".equals(type)) {
            if (listParams != null && listParams.getLimit() != null && listParams.getOffset() != null) {
                nativeQuery.setFirstResult(listParams.getOffset()).setMaxResults(listParams.getLimit());
            }
        }
        if (pattern != null) {
            if (pattern.getSearch() != null) {
                nativeQuery.setParameter("search", "%" + pattern.getSearch() + "%");
            }
            if (pattern.getAuthorId() != null) {
                nativeQuery.setParameter("authorId", listParams.getPattern().getAuthorId());
            }
            if (pattern.getRating() != null) {
                nativeQuery.setParameter("rating", listParams.getPattern().getRating());
            }
        }
        return nativeQuery;
    }

    @Override
    public Integer totalRecords(ListParams<BookPattern> listParams) {
        StringBuilder query = new StringBuilder("SELECT Count(book.id) FROM book");
        Query nativeQuery = (Query) entityManager.createNativeQuery(generateQueryWithParams(listParams, query).toString());
        nativeQuery = setParameters(listParams, nativeQuery, "totalRecords");
        BigInteger bigInteger = (BigInteger) nativeQuery.getSingleResult(); // todo: wrong implementation! Rework!
        return bigInteger.intValue();
    }

    @Override
    public Integer delete(Integer idBook) throws DaoException {
        if (idBook == null) {
            throw new DaoException("Entity id can't be null");
        }
        Book book = get(idBook);
        try {
	        // todo: why are you don't use method delete(...) ?
            entityManager.createNativeQuery("DELETE FROM author_book WHERE book_id = :bookId").setParameter("bookId", book.getId()).executeUpdate();
            // todo: for what this query? Rework! (Foreign key!)
            entityManager.createNativeQuery("DELETE FROM review WHERE book_id = :bookId").setParameter("bookId", book.getId()).executeUpdate();
            entityManager.remove(book);
        } catch (Exception e) {
            throw new DaoException();
        }
        return idBook;
    }

    @Override
    public void bulkDelete(List<Integer> idBooks) throws DaoException {
        for (Integer idBook : idBooks) {
            delete(idBook);
        }
    }
}

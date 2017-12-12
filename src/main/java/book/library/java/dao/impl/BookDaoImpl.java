package book.library.java.dao.impl;

import book.library.java.dao.BookDao;
import book.library.java.dto.BookWithAuthors;
import book.library.java.dto.ListParams;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class BookDaoImpl extends AbstractDaoImpl<Book, BookPattern> implements BookDao {

    @Override
    public void create(BookWithAuthors bookWithAuthors) throws DaoException {
        Book book = bookWithAuthors.getBook();
        List<Author> authors = bookWithAuthors.getAuthors();
        if (bookWithAuthors == null) {
            throw new DaoException("Entity can't be null");
        }
        entityManager.persist(book);
        for (Author author : authors) {
            entityManager.createNativeQuery("INSERT INTO author_book VALUES (:authorId, :bookId)")
                .setParameter("authorId", author.getId()).setParameter("bookId", book.getId())
                .executeUpdate();
        }
    }


    @Override
    public List<Book> find(ListParams<BookPattern> listParams) {
        StringBuilder startQuery = new StringBuilder("SELECT * FROM  book");
        StringBuilder query = new StringBuilder(generateQueryWithParams(listParams, startQuery));
        if (listParams.getSortParams() != null && listParams.getSortParams().getParameter() != null && listParams.getSortParams().getStatus() != null && listParams.getSortParams().getStatus()) {
            query.append(" ORDER BY :parameter, average_rating, create_date");
        }
        else {
            query.append(" ORDER BY average_rating, create_date");
        }
        Query nativeQuery = (Query) entityManager.createNativeQuery(query.toString(), Book.class);
        nativeQuery = setParameters(listParams, nativeQuery, "find");
        if (listParams.getSortParams() != null && listParams.getSortParams().getParameter() != null && listParams.getSortParams().getStatus() != null && listParams.getSortParams().getStatus()) {
            nativeQuery.setParameter("parameter", listParams.getSortParams().getParameter());
        }
        List<Book> bookList = nativeQuery.getResultList();
        bookList.forEach(book -> book.getAuthors().forEach(author -> author.getId()));
        return bookList;
    }

    public StringBuilder generateQueryWithParams(ListParams<BookPattern> listParams, StringBuilder query) {
        BookPattern pattern = listParams != null ? listParams.getPattern() : null;
        if (pattern != null) {
            if (pattern.getAuthorId() != null) {
                query.append(" JOIN author_book ON book.id = author_book.book_id");
            }
            query.append(" WHERE LOWER(name) LIKE LOWER(:search)");
            if (pattern.getAuthorId() != null) {
                query.append(" AND author_book.author_id = :authorId");
            }
            if (pattern.getRating() != null) {
                query.append(" AND book.average_rating = :rating");
            }
        }
        return query;
    }
    public Query setParameters(ListParams<BookPattern> listParams, Query nativeQuery, String type) {
        System.out.println(listParams);
        BookPattern pattern = listParams != null ? listParams.getPattern() : null;
        if (type.equals("find")) {
            if (listParams != null && listParams.getLimit() != null && listParams.getOffset() != null) {
                nativeQuery.setFirstResult(listParams.getOffset()).setMaxResults(listParams.getLimit());
            }
        }
        if (pattern != null) {
            if (pattern.getSearch() !=  null) {
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
        BigInteger bigInteger = (BigInteger) nativeQuery.getSingleResult();
        return bigInteger.intValue();
    }

    @Override
    public Integer delete(Integer idBook) throws DaoException {
        if (idBook == null) {
            throw new DaoException("Entity id can't be null");
        }
        Book book = get(idBook);
        try {
            entityManager.createNativeQuery("DELETE FROM author_book WHERE book_id = :bookId").setParameter("bookId", book.getId()).executeUpdate();
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


    @Override
    public Integer countBooksByAuthorId(Integer authorId) {
        return (int) (long) entityManager.
            createNativeQuery("SELECT Count(*) FROM book as b JOIN author_book as ab ON b.id = ab.book_id WHERE ab.author_id =: authorId").
            setParameter("authorId", authorId).getSingleResult();
    }

    @Override
    public List<Book> getByAuthor(Integer authorId) {
        StringBuilder query = new StringBuilder("SELECT * FROM books as b JOIN author_book_keys as a ON a.book_id = b.id WHERE a.author_id = '");
        query.append(authorId).append("'");
        return entityManager.createNativeQuery(query.toString(), Book.class).getResultList();
    }

    @Override
    public List<Book> getByRating(Double rating) {
        StringBuilder query = new StringBuilder("SELECT * FROM books as b JOIN reviews as r ON r.book_id = b.id WHERE r.rating = ");
        query.append(rating);
        return entityManager.createNativeQuery(query.toString(), Book.class).getResultList();
    }
}

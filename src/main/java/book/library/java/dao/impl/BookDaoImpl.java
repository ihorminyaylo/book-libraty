package book.library.java.dao.impl;

import book.library.java.dao.BookDao;
import book.library.java.dto.BookWithAuthors;
import book.library.java.exception.DaoException;
import book.library.java.list.ListParams;
import book.library.java.model.Author;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl extends AbstractDao<Book, BookPattern> implements BookDao {

    @Override
    public Integer create(BookWithAuthors bookWithAuthors) throws DaoException {
        Book book = bookWithAuthors.getBook();
        if (null == bookWithAuthors) {
            throw new DaoException("AbstractEntity can't be null");
        }
        List<Author> authors = bookWithAuthors.getAuthors();
        book.setAuthors(authors);
        entityManager.persist(book);
        return book.getId();
    }

    @Override
    public List<Book> readTopFive() {
        List<Book> books = entityManager.createNativeQuery("SELECT * FROM book ORDER BY average_rating", Book.class).setFirstResult(0).setMaxResults(5).getResultList();
        books.forEach(book -> {
            book.getAuthors().size();
        });
        return books;
    }

    @Override
    public Integer update(BookWithAuthors bookWithAuthors) throws DaoException {
        Book book = bookWithAuthors.getBook();
        if (null == bookWithAuthors) {
            throw new DaoException("AbstractEntity can't be null");
        }
        List<Author> authors = bookWithAuthors.getAuthors();
        book.setAuthors(authors);
        entityManager.merge(book);
        return book.getId();
    }

    @Override
    public List<Book> find(ListParams<BookPattern> listParams) throws DaoException {
        StringBuilder query = new StringBuilder("SELECT * FROM  book");
        query = generateQueryWithParams(listParams, query);
        generateQueryWithSortParams(listParams, query);
        Query nativeQuery = (Query) entityManager.createNativeQuery(query.toString(), Book.class);
        nativeQuery = setParameters(listParams, nativeQuery, true);
        List<Book> bookList = nativeQuery.getResultList();
        bookList.forEach(book -> book.getAuthors().size());
        return bookList;
    }

    private StringBuilder generateQueryWithParams(ListParams<BookPattern> listParams, StringBuilder query) {
        if (listParams.getPattern() != null) {
            BookPattern pattern = listParams.getPattern();
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
            }
        } else {
            query.append(" ORDER BY average_rating, create_date");
        }
        return query;
    }

    private Query setParameters(ListParams<BookPattern> listParams, Query nativeQuery, Boolean typeQueryFind) {
        BookPattern pattern = listParams != null ? listParams.getPattern() : null;
        if (typeQueryFind) {
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
                nativeQuery.setParameter("ratingSmall", listParams.getPattern().getRating() - 0.5).setParameter("ratingBig", listParams.getPattern().getRating() + 0.5);
            }
        }
        return nativeQuery;
    }

    @Override
    public Integer totalRecords(ListParams<BookPattern> listParams) {
        StringBuilder query = new StringBuilder("SELECT Count(book.id) FROM book");
        Query nativeQuery = (Query) entityManager.createNativeQuery(generateQueryWithParams(listParams, query).toString());
        nativeQuery = setParameters(listParams, nativeQuery, false);
        return Integer.parseInt(nativeQuery.getSingleResult().toString());
    }

    @Override
    public void update(Book book) throws DaoException {
        if (book == null || book.getId() == null) {
            throw new DaoException("AbstractEntity can't be null");
        }
        entityManager.merge(book);
    }

    @Override
    public void bulkDelete(List<Integer> idBooks) throws DaoException {
        for (Integer idBook : idBooks) {
            delete(idBook);
        }
    }
}

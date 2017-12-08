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
        BookPattern pattern = listParams != null ? listParams.getPattern() : null;
        Query nativeQuery = (Query) entityManager.createNativeQuery(generateFindQuery(listParams).toString(), Book.class);
        if (listParams != null && listParams.getLimit() != null && listParams.getOffset() != null) {
            nativeQuery.setFirstResult(listParams.getOffset()).setMaxResults(listParams.getLimit());
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
        if (listParams.getSortParams() != null && listParams.getSortParams().getParameter() != null && listParams.getSortParams().getStatus() != null && listParams.getSortParams().getStatus()) {
            nativeQuery.setParameter("parameter", listParams.getSortParams().getParameter());
        }
        List<Book> bookList = nativeQuery.getResultList();
        bookList.forEach(book -> book.getAuthors().forEach(author -> author.getId()));
        return bookList;
    }

    private StringBuilder generateFindQuery(ListParams<BookPattern> listParams) {
        BookPattern pattern = listParams != null ? listParams.getPattern() : null;
        StringBuilder query = new StringBuilder("SELECT * FROM  book");
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
        if (listParams.getSortParams() != null && listParams.getSortParams().getParameter() != null && listParams.getSortParams().getStatus() != null && listParams.getSortParams().getStatus()) {
            query.append(" ORDER BY :parameter, average_rating, create_date");
        }
        else {
            query.append(" ORDER BY average_rating, create_date");
        }
        return query;
    }

    @Override
    public void delete(Integer idBook) throws DaoException {
        if (idBook == null) {
            throw new DaoException("Entity id can't be null");
        }
        Book book = get(idBook);
        try {
            entityManager.createNativeQuery("DELETE FROM author_book WHERE book_id = :bookId").setParameter("bookId", book.getId()).executeUpdate();
            entityManager.remove(book);
        } catch (Exception e) {
            throw new DaoException();
        }
    }

    @Override
    public void bulkDelete(List<Integer> idBooks) throws DaoException {
        for (Integer idBook : idBooks) {
            if (idBook == null) {
                throw new DaoException("Entity id can't be null");
            }
            Book book = get(idBook);
            try {
                entityManager.createNativeQuery("DELETE FROM author_book WHERE book_id = :bookId").setParameter("bookId", book.getId()).executeUpdate();
                entityManager.remove(book);
            } catch (Exception e) {
                throw new DaoException();
            }
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

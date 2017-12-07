package book.library.java.dao.impl;

import book.library.java.dao.BookDao;
import book.library.java.dto.BookWithAuthors;
import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.Book;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Queue;

@Repository
public class BookDaoImpl extends AbstractDaoImpl<Book> implements BookDao {

    @Override
    public void create(BookWithAuthors bookWithAuthors) throws DaoException {
        Book book = bookWithAuthors.getBook();
        List<Author> authors = bookWithAuthors.getAuthors();
        if (bookWithAuthors == null) {
            throw new DaoException("Entity can't be null");
        }
        entityManager.persist(book);
        for (Author author: authors) {
            entityManager.createNativeQuery("INSERT INTO author_book VALUES (:authorId, :bookId)")
                .setParameter("authorId", author.getId()).setParameter("bookId", book.getId())
                .executeUpdate();
        }
    }
/*WHERE name LIKE '%word1%'*/
    @Override
    public List<Book> find(ReadParamsDto readParamsDto) {
        String filterBy = "";
        if (readParamsDto.getFilterBy() != null) {
            filterBy = readParamsDto.getFilterBy();
        }
        if (readParamsDto.getPattern() != null && readParamsDto.getPattern().toString().contains(("byAuthor"))) {
            Integer authorId = Integer.parseInt(readParamsDto.getPattern().toString().substring(9));
            return entityManager
                .createNativeQuery("SELECT * FROM  book JOIN author_book ON book.id = author_book.book_id " +
                    "WHERE author_book.author_id = :authorId AND name LIKE :search ORDER BY average_rating, create_date LIMIT :limit OFFSET :offset", Book.class)
                .setParameter("limit",readParamsDto.getLimit()).setParameter("offset",readParamsDto.getOffset()).setParameter("authorId",authorId).setParameter("search", "%" + filterBy + "%").getResultList();
        }
        if (readParamsDto.getPattern() != null && readParamsDto.getPattern().toString().contains(("byRating"))) {
            Integer rating = Integer.parseInt(readParamsDto.getPattern().toString().substring(9));
            return entityManager
                .createNativeQuery("SELECT * FROM  book WHERE average_rating = :rating AND name LIKE :search ORDER BY create_date LIMIT :limit OFFSET :offset", Book.class)
                .setParameter("limit",readParamsDto.getLimit()).setParameter("offset",readParamsDto.getOffset()).setParameter("rating",rating).setParameter("search", "%" + filterBy + "%").getResultList();
        }
        return entityManager
            .createNativeQuery("SELECT * FROM  book WHERE LOWER(name) LIKE LOWER(:search) ORDER BY average_rating, create_date LIMIT :limit OFFSET :offset", Book.class)
            .setParameter("limit",readParamsDto.getLimit()).setParameter("offset",readParamsDto.getOffset()).setParameter("search", "%" + filterBy + "%").getResultList();


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

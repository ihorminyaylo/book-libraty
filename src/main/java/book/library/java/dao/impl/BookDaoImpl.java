package book.library.java.dao.impl;

import book.library.java.dao.BookDao;
import book.library.java.dto.BookWithAuthors;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                .setParameter("authorId", author.getId()).setParameter("bookId", book.getId());
        }
    }


    @Override
    public void bulkDelete(List<Integer> idBooks) throws DaoException {
        for (Integer idBook : idBooks) {
            if (idBook == null) {
                throw new DaoException("Entity id can't be null");
            }
            Book book = get(idBook);
            entityManager.createNativeQuery("DELETE FROM author_book WHERE book_id = :bookId").setParameter("bookId", book.getId());
            try {
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

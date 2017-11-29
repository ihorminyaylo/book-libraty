package book.library.java.dao.impl;

import book.library.java.dao.BookDao;
import book.library.java.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl extends AbstractDaoImpl<Book> implements BookDao {

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

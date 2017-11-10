package book.library.java.dao.impl;

import book.library.java.dao.AbstractDao;
import book.library.java.dao.AuthorDao;
import book.library.java.model.Author;
import book.library.java.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorDaoImpl extends AbstractDao <Author> implements AuthorDao {
    @Override
    public List<Author> getByAverageRating() {
        StringBuilder query = new StringBuilder("SELECT * FROM authors as a JOIN author_book_keys ab ON ab.author_id = a.id JOIN reviews r ON r.book_id = ab.book_id WHERE r.rating = (SELECT AVG(rating) FROM reviews)");
        return entityManager.createNativeQuery(query.toString(), Author.class).getResultList();
    }
}

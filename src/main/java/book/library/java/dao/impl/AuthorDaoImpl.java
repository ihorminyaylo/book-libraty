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
        StringBuilder query = new StringBuilder("SELECT * FROM authors INNER JOIN author_book_keys ON authors.id = author_book_keys.author_id INNER JOIN reviews ON author_book_keys.book_id = reviews.book_id");
        return entityManager.createNativeQuery(query.toString(), Author.class).getResultList();
    }
}

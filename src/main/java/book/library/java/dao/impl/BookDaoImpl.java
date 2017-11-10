package book.library.java.dao.impl;

import book.library.java.dao.AbstractDao;
import book.library.java.dao.BookDao;
import book.library.java.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImpl extends AbstractDao<Book> implements BookDao {


    //todo change SQL query on HQL
    @Override
    public List<Book> getByAuthor(String authorId) {
        StringBuilder query = new StringBuilder("SELECT * FROM books as b JOIN author_book_keys as a ON a.book_id = b.id WHERE a.author_id = '");
        query.append(authorId).append("'");
        return entityManager.createNativeQuery(query.toString(), Book.class).getResultList();
    }
}

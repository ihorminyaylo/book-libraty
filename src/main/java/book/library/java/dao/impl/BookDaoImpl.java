package book.library.java.dao.impl;

import book.library.java.dao.AbstractDao;
import book.library.java.dao.BookDao;
import book.library.java.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl extends AbstractDao<Book> implements BookDao {
}

package book.library.java.dao.impl;

import book.library.java.dao.AbstractDao;
import book.library.java.dao.AuthorDao;
import book.library.java.model.Author;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorDaoImpl extends AbstractDao<Author> implements AuthorDao {
}

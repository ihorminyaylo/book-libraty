package book.library.java.service.impl;

import book.library.java.dao.AbstractDao;
import book.library.java.dao.AuthorDao;
import book.library.java.model.Author;
import book.library.java.service.AuthorService2;
import book.library.java.service.impl.AbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AuthorServiceImpl2 extends AbstractServiceImpl<Author> implements AuthorService2{
    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl2(@Qualifier("authorDaoImpl") AbstractDao<Author> entityDaoType) {
        super(entityDaoType);
        this.authorDao = (AuthorDao) entityDaoType;
    }
}

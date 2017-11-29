package book.library.java.service.impl;

import book.library.java.dao.BookDao;
import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dao.AuthorDao;
import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class AuthorServiceImpl extends AbstractServiceImpl<Author> implements AuthorService {
    private final AuthorDao authorDao;
    private final BookDao bookDao;

    @Autowired
    public AuthorServiceImpl(@Qualifier("authorDaoImpl") AbstractDaoImpl<Author> entityDaoType, BookDao bookDao) {
        super(entityDaoType);
        authorDao = (AuthorDao) entityDaoType;
        this.bookDao = bookDao;
    }

    @Override
    public void update(Author author) throws BusinessException {
        if (author.getFirstName() == null || author.getFirstName().equals("")) {
            throw new BusinessException("First name isn't correct");
        }
        if (author.getFirstName() == null || author.getFirstName().equals("")) {
            throw new BusinessException("Last name isn't correct");
        }
        try {
            authorDao.update(author);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void create(Author author) throws BusinessException {
        if (author.getFirstName() == null || author.getFirstName().equals("")) {
            throw new BusinessException("First name isn't correct");
        }
        if (author.getFirstName() == null || author.getFirstName().equals("")) {
            throw new BusinessException("Last name isn't correct");
        }
        try {
            authorDao.create(author);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public <P> EntitiesAndPageDto<Author> read(ReadParamsDto<P> readParamsDto) throws BusinessException {
        EntitiesAndPageDto<Author> list = super.read(readParamsDto);
        list.getList().stream().forEach(author -> author.setBooks(new ArrayList<>()));
        return list;
    }

    @Override
    public List<Author> delete(List<Integer> idEntities) throws BusinessException {
        List<Author> notRemove = new ArrayList<>();
        List<Integer> authorWithBooks = new ArrayList<>();
        for (Integer idEntity : idEntities) {
            if (bookDao.countBooksByAuthorId(idEntity) > 0) {
                authorWithBooks.add(idEntity);
            }
        }
        try {
            notRemove = authorDao.delete(authorWithBooks);
        } catch (Exception e) {
            throw new BusinessException();
        }
        return notRemove;
    }
}

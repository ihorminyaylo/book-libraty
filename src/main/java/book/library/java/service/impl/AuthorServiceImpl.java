package book.library.java.service.impl;

import book.library.java.dao.BookDao;
import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dao.AuthorDao;
import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.dto.ListParams;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;
import book.library.java.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class AuthorServiceImpl extends AbstractServiceImpl<Author, AuthorPattern> implements AuthorService {
    private final AuthorDao authorDao;
    private final BookDao bookDao;

    @Autowired
    public AuthorServiceImpl(@Qualifier("authorDaoImpl") AbstractDaoImpl<Author, AuthorPattern> entityDaoType, BookDao bookDao) {
        super(entityDaoType);
        authorDao = (AuthorDao) entityDaoType;
        this.bookDao = bookDao;
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
    public EntitiesAndPageDto<Author> read(ListParams listParams) throws BusinessException {
        EntitiesAndPageDto<Author> entitiesAndPageDto;
        /*if (listParams.getFilterBy() == null && listParams.getPattern() == null && listParams.getOffset() == null && listParams.getLimit() == null) {
            return new EntitiesAndPageDto<>(authorDao.findAll(), authorDao.totalRecords());
        }*/
        if (listParams.getPattern() != null && listParams.getPattern().toString().contains("byId")) {
            Author author = authorDao.get(Integer.parseInt(listParams.getPattern().toString().substring(5)));
            List<Author> authors = new ArrayList<>();
            authors.add(author);
            entitiesAndPageDto = new EntitiesAndPageDto<>(authors, authorDao.totalRecords(null));
        }
        else {
            entitiesAndPageDto = super.read(listParams);
/*
            entitiesAndPageDto.getList().forEach(author -> author.setBooks(new ArrayList<>()));
*/
        }
        return entitiesAndPageDto;
    }

    @Override
    public List<Author> readAll() {
        return authorDao.findAll();
    }

    @Override
    public List<Author> readByBook(Integer idBook) throws BusinessException {
        try {
            return authorDao.readByBook(idBook);
        } catch (DaoException e) {
            throw new BusinessException();
        }
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
    public List<Author> bulkDelete(List<Integer> idAuthors) throws BusinessException {
        try {
            return authorDao.bulkDeleteAuthors(idAuthors);
        } catch (DaoException e) {
            throw new BusinessException();
        }
    }

    /*@Override
    public void bulkDelete(List<Integer> idEntities) throws BusinessException {
        super.bulkDelete(idEntities);
        *//*List<Author> notRemove = new ArrayList<>();
        List<Integer> authorWithBooks = new ArrayList<>();
        for (Integer idEntity : idEntities) {
            if (bookDao.countBooksByAuthorId(idEntity) > 0) {
                authorWithBooks.add(idEntity);
            }
        }
        try {
            notRemove = authorDao.bulkDelete(authorWithBooks);
        } catch (Exception e) {
            throw new BusinessException();
        }
        return notRemove;*//*
    }*/
}

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
import java.util.List;


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
        EntitiesAndPageDto<Author> entitiesAndPageDto;
        if (readParamsDto.getFilterBy() == null && readParamsDto.getPattern() == null && readParamsDto.getOffset() == null && readParamsDto.getLimit() == null) {
            return new EntitiesAndPageDto<>(authorDao.findAll(), authorDao.totalRecords());
        }
        if (readParamsDto.getPattern() != null && readParamsDto.getPattern().toString().contains("byId")) {
            Author author = authorDao.get(Integer.parseInt(readParamsDto.getPattern().toString().substring(5)));
            List<Author> authors = new ArrayList<>();
            authors.add(author);
            entitiesAndPageDto = new EntitiesAndPageDto<>(authors, authorDao.totalRecords());
        }
        else {
            entitiesAndPageDto = super.read(readParamsDto);
            entitiesAndPageDto.getList().forEach(author -> author.setBooks(new ArrayList<>()));
        }
        return entitiesAndPageDto;
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

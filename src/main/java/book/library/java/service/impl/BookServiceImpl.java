package book.library.java.service.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.dao.BookDao;
import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dto.BookWithAuthors;
import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.dto.ListParams;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;
import book.library.java.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl extends AbstractServiceImpl<Book, BookPattern> implements BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;

    @Autowired
    public BookServiceImpl(@Qualifier("bookDaoImpl") AbstractDaoImpl<Book, BookPattern> entityDaoType, AuthorDao authorDao) {
        super(entityDaoType);
        bookDao = (BookDao) entityDaoType;
        this.authorDao = authorDao;
    }

    @Override
    public void create(BookWithAuthors bookWithAuthors) throws BusinessException {
        try {
            bookDao.create(bookWithAuthors);

        } catch (Exception e) {
            throw new BusinessException();
        }
    }

    @Override
    public EntitiesAndPageDto<Book> read(ListParams listParams) throws BusinessException {
        List<Book> listEntity;
        Integer totalItems = bookDao.totalRecords();
        listEntity = bookDao.find(listParams);
        return new EntitiesAndPageDto<>(listEntity, totalItems);
    }

    @Override
    public void delete(Integer idBook) throws BusinessException {
        try {
            bookDao.delete(idBook);
        } catch (DaoException e) {
            throw new BusinessException();
        }
    }

    @Override
    public void bulkDelete(List<Integer> idBooks) throws BusinessException {
        try {
            bookDao.bulkDelete(idBooks);
        } catch (DaoException e) {
            throw new BusinessException();
        }
    }
}

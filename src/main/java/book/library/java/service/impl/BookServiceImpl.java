package book.library.java.service.impl;

import book.library.java.dao.BookDao;
import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dto.BookWithAuthors;
import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Book;
import book.library.java.model.ListParams;
import book.library.java.model.pattern.BookPattern;
import book.library.java.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl extends AbstractServiceImpl<Book, BookPattern> implements BookService {
    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(@Qualifier("bookDaoImpl") AbstractDaoImpl<Book, BookPattern> entityDaoType) {
        super(entityDaoType);
        bookDao = (BookDao) entityDaoType;
    }

    @Override
    public Integer create(BookWithAuthors bookWithAuthors) throws BusinessException {
        try {
            validateBook(bookWithAuthors.getBook());
            return bookDao.create(bookWithAuthors);

        } catch (Exception e) {
            throw new BusinessException();
        }
    }

    @Override
    public List<Book> readTop(Integer count) {
        return bookDao.readTop(count);
    }

    @Override
    public BigDecimal getAverageRating() throws DaoException {
        return bookDao.getAverageRating();
    }

    @Override
    public EntitiesAndPageDto<Book> read(ListParams listParams) throws BusinessException, DaoException {
        System.out.println(listParams.getLimit() + "offset" + listParams.getOffset());
        Integer totalItems = bookDao.totalRecords(listParams);
        List<Book> listEntity = bookDao.find(listParams);
        listEntity.forEach(book -> book.setReviews(null));
        return new EntitiesAndPageDto<>(listEntity, totalItems);
    }

    @Override
    public void update(Book book) throws BusinessException {
        try {
            validateBook(book);
            bookDao.update(book);
        } catch (Exception e) {
            throw new BusinessException();
        }
    }


    @Override
    public Integer delete(Integer idBook) throws BusinessException {
        try {
            bookDao.delete(idBook);
        } catch (DaoException e) {
            throw new BusinessException();
        }
        return idBook;
    }

    @Override
    public void bulkDelete(List<Integer> idBooks) throws BusinessException {
        try {
            bookDao.bulkDelete(idBooks);
        } catch (DaoException e) {
            throw new BusinessException();
        }
    }

    void validateBook(Book book) throws BusinessException {
        if (book.getName() == null || book.getName().isEmpty()) {
            throw new BusinessException("Name of book isn't correct");
        }
        if (book.getPublisher() == null || book.getPublisher().isEmpty()) {
            throw new BusinessException("Publisher of book isn't correct");
        }
        if (book.getYearPublished() == 0) {
            throw new BusinessException("Year published of book isn't correct");
        }
    }
}

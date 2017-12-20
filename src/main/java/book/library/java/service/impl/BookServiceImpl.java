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
    private final BookDao bookDao; // todo: useless - remove

    @Autowired
    // todo: why: @Qualifier("bookDaoImpl") AbstractDaoImpl<Book, BookPattern> entityDaoType ??
    public BookServiceImpl(@Qualifier("bookDaoImpl") AbstractDaoImpl<Book, BookPattern> entityDaoType) {
        super(entityDaoType);
        bookDao = (BookDao) entityDaoType;
    }

    @Override
    public Integer create(BookWithAuthors bookWithAuthors) throws BusinessException {
        try {
            validateBook(bookWithAuthors.getBook()); // todo: why here?
            return bookDao.create(bookWithAuthors);

        } catch (Exception e) {
            throw new BusinessException(); // todo: must add base exception into BusinessException
        }
    }

    @Override
    public List<Book> readTop(Integer count) {
        return bookDao.readTop(count);
    }

    @Override
    public BigDecimal getAverageRating() throws DaoException {  // todo: is this method really need?
        return bookDao.getAverageRating();
    }

    @Override
    public EntitiesAndPageDto<Book> read(ListParams listParams) throws BusinessException, DaoException { // todo: generic for ListParams!, why DaoException in signature?
        System.out.println(listParams.getLimit() + "offset" + listParams.getOffset()); // todo: ???
	    // todo: where is condition for search?
        Integer totalItems = bookDao.totalRecords(listParams);
        List<Book> listEntity = bookDao.find(listParams);
        listEntity.forEach(book -> book.setReviews(null)); // todo: WTF?
        return new EntitiesAndPageDto<>(listEntity, totalItems);
    }

    @Override
    public void update(Book book) throws BusinessException {
        try {
            validateBook(book);
            bookDao.update(book);
        } catch (Exception e) {
            throw new BusinessException(); // todo: must add base exception into BusinessException
        }
    }


    @Override
    public Integer delete(Integer idBook) throws BusinessException {
        try {
            bookDao.delete(idBook);
        } catch (DaoException e) {
            throw new BusinessException(); // todo: must add base exception into BusinessException
        }
        return idBook;
    }

    @Override
    public void bulkDelete(List<Integer> idBooks) throws BusinessException {
        try {
            bookDao.bulkDelete(idBooks);
        } catch (DaoException e) {
            throw new BusinessException(); // todo: must add base exception into BusinessException
        }
    }

    void validateBook(Book book) throws BusinessException { // todo: Access can be private. Method 'validateBook()' may be 'static'
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

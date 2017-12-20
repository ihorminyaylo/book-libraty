package book.library.java.service.impl;

import book.library.java.dao.BookDao;
import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dto.BookWithAuthors;
import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Book;
import book.library.java.list.ListParams;
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
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Book> readTop(Integer count) {
        return bookDao.readTop(count);
    }

    @Override
    public ListEntityPage<Book> read(ListParams<BookPattern> listParams) throws BusinessException {
        System.out.println(listParams.getLimit() + "offset" + listParams.getOffset()); // todo: ???
	    // todo: where is condition for search?
        Integer totalItems = bookDao.totalRecords(listParams);
        List<Book> listEntity = null;
        try {
            listEntity = bookDao.find(listParams);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
        listEntity.forEach(book -> book.setReviews(null)); // todo: WTF?
        return new ListEntityPage<>(listEntity, totalItems);
    }

    @Override
    public void update(Book book) throws BusinessException {
        try {
            validateBook(book);
            bookDao.update(book);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }


    @Override
    public Integer delete(Integer idBook) throws BusinessException {
        try {
            bookDao.delete(idBook);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
        return idBook;
    }

    @Override
    public void bulkDelete(List<Integer> idBooks) throws BusinessException {
        try {
            bookDao.bulkDelete(idBooks);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    private static void validateBook(Book book) throws BusinessException {
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

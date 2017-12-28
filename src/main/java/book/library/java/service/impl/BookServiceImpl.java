package book.library.java.service.impl;

import book.library.java.dao.BookDao;
import book.library.java.dao.impl.BookDaoImpl;
import book.library.java.dto.BookDto;
import book.library.java.dto.BookWithAuthors;
import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Book;
import book.library.java.list.ListParams;
import book.library.java.model.pattern.BookPattern;
import book.library.java.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl extends AbstractServiceImpl<Book, BookPattern> implements BookService {
    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDaoImpl bookDao) {
        super(bookDao);
        this.bookDao = bookDao;
    }

    @Override
    public Integer create(BookWithAuthors bookWithAuthors) throws BusinessException {
        validateEntity(bookWithAuthors.getBook());
        try {
            return bookDao.create(bookWithAuthors);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public BookDto readByBookId(Integer idBook) throws BusinessException {
        try {
            Book book = bookDao.get(idBook);
            book.getAuthors().size();
            return new BookDto(book);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<BookDto> readTopFive() {
        return bookDao.readTopFive().stream().map(book -> new BookDto(book)).collect(Collectors.toList());    }

    @Override
    public ListEntityPage<BookDto> readBooks(ListParams<BookPattern> listParams) throws BusinessException {
        Integer totalItems = bookDao.totalRecords(listParams);
        List<BookDto> listEntity = new ArrayList<>();
        try {
            listEntity = bookDao.find(listParams).stream().map(book -> new BookDto(book)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
        return new ListEntityPage(listEntity, totalItems);
    }

    @Override
    public Integer updateBook(BookWithAuthors bookWithAuthors) throws BusinessException {
        validateEntity(bookWithAuthors.getBook());
        try {
            return bookDao.update(bookWithAuthors);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void bulkDelete(List<Integer> idBooks) throws BusinessException {
        try {
            bookDao.bulkDelete(idBooks);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void validateEntity(Book book) throws BusinessException {
        if (StringUtils.isBlank(book.getName())) {
            throw new BusinessException("Name of book isn't correct");
        }
    }
}

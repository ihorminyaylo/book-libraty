package book.library.java.service.impl;

import book.library.java.dao.BookDao;
import book.library.java.dto.BookDto;
import book.library.java.dto.ListEntityPage;
import book.library.java.dto.ReviewPageDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.list.ListParams;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;
import book.library.java.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl extends AbstractService<BookDao, Book, BookPattern, BookDto> implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        super(bookDao);
    }

    @Override
    public Integer create(Book book) throws BusinessException {
        validateEntity(book);
        try {
            return getDao().create(book);

        } catch (Exception e) {
            log.error("in create() exception - [{}] ", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public BookDto readById(Integer idBook) throws BusinessException {
        try {
            Book book = getDao().get(idBook);
            book.getAuthors().size();
            return new BookDto(book);
        } catch (DaoException e) {
            log.error("in readById() exception - [{}] ", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<BookDto> readTopFive() {
        List<Book> books = getDao().findTopFive();
        books.forEach(book -> book.setAverageRating(book.getAverageRating().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        return books.stream().map(book -> new BookDto(book)).collect(Collectors.toList());
    }

    @Override
    public List<ReviewPageDto> getCountOfEachRating() {
        return getDao().getCountOfEachRating();
    }

    @Override
    public ListEntityPage<BookDto> readBooks(ListParams<BookPattern> listParams) throws BusinessException {
        Integer totalItems;
        try {
            totalItems = getDao().totalRecords(listParams);
        } catch (DaoException e) {
            log.error("in readBooks() exception - [{}] ", e);
            throw new BusinessException(e);
        }
        List<BookDto> listEntity;
        try {
            List<Book> books = getDao().find(listParams);
            books.forEach(book -> {
                book.getAuthors().size();
                if (book.getAverageRating() != null) {
                    book.setAverageRating(book.getAverageRating().setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
            });
            listEntity = books.stream().map(book -> new BookDto(book)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("in readBooks() exception - [{}] ", e);
            throw new BusinessException(e);
        }
        return new ListEntityPage<>(listEntity, totalItems);
    }

    @Override
    public void validateEntity(Book book) throws BusinessException {
        if (StringUtils.isBlank(book.getName())) {
            log.error("in validateEntity() exception - Name of book isn't correct");
            throw new BusinessException("Name of book isn't correct");
        }
    }
}

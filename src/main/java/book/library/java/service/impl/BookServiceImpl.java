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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl extends AbstractService<BookDao, Book, BookPattern, BookDto> implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);
    private static final Pattern PATTERN = Pattern.compile("^[0-9]{3}-[0-9]-[0-9]{2}-[0-9]{6}-[0-9]");

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
            LOGGER.error("in create() exception!", e);
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
            LOGGER.error("in readById() exception!", e);
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
            LOGGER.error("in readBooks() exception!", e);
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
            LOGGER.error("in readBooks() exception!", e);
            throw new BusinessException(e);
        }
        return new ListEntityPage<>(listEntity, totalItems);
    }

    @Override
    public void validateEntity(Book book) throws BusinessException {
        if (StringUtils.isBlank(book.getName())) {
            LOGGER.error("in validateEntity() exception!");
            throw new BusinessException("Name of book isn't correct");
        }
        String isbn = book.getIsbn();
        if (!StringUtils.isBlank(isbn)) {
            Matcher matcher = PATTERN.matcher(isbn);
            if (!matcher.matches()) {
                LOGGER.error("in validateEntity() exception!");
                throw new BusinessException("The ISBN must be like XXX-X-XX-XXXXXX-X where \"X\" - number.");
            }
            if (getDao().checkISBN(isbn)) {
                LOGGER.error("in validateEntity() exception!");
                throw new BusinessException("The isbn already exist");
            }
        }
    }
}

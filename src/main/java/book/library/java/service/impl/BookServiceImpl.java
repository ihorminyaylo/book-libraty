package book.library.java.service.impl;

import book.library.java.dao.BookDao;
import book.library.java.dto.BookDto;
import book.library.java.dto.BookWithAuthors;
import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.list.ListParams;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;
import book.library.java.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl extends AbstractService<Book, BookPattern> implements BookService {

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        super(bookDao);
    }

    @Override
    public Integer create(BookWithAuthors bookWithAuthors) throws BusinessException {
        validateEntity(bookWithAuthors.getBook());
        try {
            return ((BookDao) getDao()).create(bookWithAuthors);

        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public BookDto readByBookId(Integer idBook) throws BusinessException {
        try {
            Book book = getDao().get(idBook);
            book.getAuthors().size();
            return new BookDto(book);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public List<BookDto> readTopFive() {
        List<Book> books = ((BookDao) getDao()).readTopFive();
        books.forEach(book -> book.setAverageRating(book.getAverageRating().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        return books.stream().map(book -> new BookDto(book)).collect(Collectors.toList());
    }

    @Override
    public ListEntityPage<BookDto> readBooks(ListParams<BookPattern> listParams) throws BusinessException {
        Integer totalItems = getDao().totalRecords(listParams);
        List<BookDto> listEntity;
        try {
            List<Book> books = getDao().find(listParams);
            books.forEach(book -> {
                if (book.getAverageRating() != null) {
                    book.setAverageRating(book.getAverageRating().setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }});
            listEntity = books.stream().map(book -> new BookDto(book)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return new ListEntityPage<>(listEntity, totalItems);
    }

    @Override
    public Integer updateBook(BookWithAuthors bookWithAuthors) throws BusinessException {
        validateEntity(bookWithAuthors.getBook());
        try {
            return ((BookDao) getDao()).update(bookWithAuthors);

        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void bulkDelete(List<Integer> idBooks) throws BusinessException {
        try {
            ((BookDao) getDao()).bulkDelete(idBooks);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void validateEntity(Book book) throws BusinessException {
        if (StringUtils.isBlank(book.getName())) {
            throw new BusinessException("Name of book isn't correct");
        }
    }
}

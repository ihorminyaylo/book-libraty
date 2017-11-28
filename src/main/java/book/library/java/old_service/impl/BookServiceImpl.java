package book.library.java.old_service.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.dao.BookDao;
import book.library.java.dao.ReviewDao;
import book.library.java.dto.*;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.mapper.BookMapper;
import book.library.java.model.Review;
import book.library.java.old_service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final ReviewDao reviewDao;
    private final AuthorDao authorDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao, ReviewDao reviewDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.reviewDao = reviewDao;
        this.authorDao = authorDao;
    }

    @Override
    public void create(BookDto bookDto) throws DaoException {
        bookDao.create(BookMapper.MAPPER.fromDto(bookDto));
    }

    @Override
    public BooksAndPageDto read(ReadParamsDto readParamsDto) throws BusinessException {
        List<BookDto> bookDtoList = new ArrayList<>();
        BigInteger pages = BigInteger.valueOf(0);
        /*if (readParamsDto.getPage() != null && readParamsDto.getPageSize() != null) {
            Integer page = Integer.parseInt(readParamsDto.getPage());
            Integer pageSize = Integer.parseInt(readParamsDto.getPageSize());
            BigInteger totalRecords = bookDao.totalRecords();
            pages = (totalRecords.add(BigInteger.valueOf(pageSize)).subtract(BigInteger.valueOf(1))).divide(BigInteger.valueOf(pageSize));
            int correctPage = BigInteger.valueOf(page).compareTo(pages);
            if (page < 0 || correctPage == 1) {
                throw new BusinessException("Page can't be less 0 and more '"+pages+"'");
            }
            if (page != 1) {
                page = (page - 1)* pageSize + 1;
            }
            bookDtoList = bookDao.find(ReadParamsDto readParamsDto).stream().map(BookMapper.MAPPER :: toDto).collect(Collectors.toList());
        }
        else {
            bookDtoList = bookDao.findAll().stream().map(BookMapper.MAPPER :: toDto).collect(Collectors.toList());
            pages = BigInteger.valueOf(1);
        }*/
        return new BooksAndPageDto(bookDtoList, pages);
    }

    @Override
    public void update(BookDto bookDto) throws DaoException {
        bookDao.update(BookMapper.MAPPER.fromDto(bookDto));
    }

    @Override
    public void delete(List<Integer> listIdBooks) throws DaoException {
        for (Integer idBook : listIdBooks) {
            List<Review> reviewList = reviewDao.getByBookId(idBook);
            if (!reviewList.isEmpty()) {
                reviewList.stream().forEach(review -> {
                    try {
                        reviewDao.delete(review.getId());
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                });
            }
            bookDao.delete(idBook);
        }
    }





    //todo: remove
    @Override
    public List<BookDto> readOld(Map<String, String> params) throws Exception {
        if (params.get("authorId") != null) {
            return bookDao.getByAuthor(Integer.parseInt(params.get("authorId"))).stream().map(BookMapper.MAPPER :: toDto).collect(Collectors.toList());
        }
        if (params.get("rating") != null) {
            Double rating = Double.parseDouble(params.get("rating"));
            if (rating >= 0 && rating <= 5) {
                return bookDao.getByRating(rating).stream().map(BookMapper.MAPPER::toDto).collect(Collectors.toList());
            }
        }
        return bookDao.findAll().stream().map(BookMapper.MAPPER :: toDto).collect(Collectors.toList());
    }
}

package book.library.java.service.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.dao.BookDao;
import book.library.java.dao.ReviewDao;
import book.library.java.dto.BookDto;
import book.library.java.mapper.BookMapper;
import book.library.java.model.Review;
import book.library.java.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
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
    public void create(BookDto bookDto) {
        bookDao.create(BookMapper.MAPPER.fromDto(bookDto));
    }

    @Override
    @Transactional
    public List<BookDto> read(Map<String, String> params) throws Exception {
        if (params.get("authorId") != null) {
            return bookDao.getByAuthor(params.get("authorId")).stream().map(BookMapper.MAPPER :: toDto).collect(Collectors.toList());
        }
        if (params.get("rating") != null) {
            Double rating = Double.parseDouble(params.get("rating"));
            if (rating >= 0 && rating <= 5) {
                return bookDao.getByRating(rating).stream().map(BookMapper.MAPPER::toDto).collect(Collectors.toList());
            }
        }
        return bookDao.findAll().stream().map(BookMapper.MAPPER :: toDto).collect(Collectors.toList());
    }

    @Override
    public void update(BookDto bookDto) {
        bookDao.update(BookMapper.MAPPER.fromDto(bookDto));
    }

    @Override
    public void delete(List<String> listIdBooks) {
        for (String idBook : listIdBooks) {
            List<Review> reviewList = reviewDao.getByBookId(idBook);
            if (!reviewList.isEmpty()) {
                reviewList.stream().forEach(review -> reviewDao.delete(review.getId()));
            }
            bookDao.delete(idBook);
        }
    }
}

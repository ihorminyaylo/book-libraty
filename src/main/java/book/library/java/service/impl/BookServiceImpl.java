package book.library.java.service.impl;

import book.library.java.dao.BookDao;
import book.library.java.dto.BookDto;
import book.library.java.mapper.BookMapper;
import book.library.java.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void create(BookDto bookDto) {
        bookDao.add(BookMapper.MAPPER.fromDto(bookDto));
    }

    @Override
    public List<BookDto> read(Map<String, String> params) throws Exception {
        if (params.get("authorId") != null) {
            return bookDao.getByAuthor(params.get("authorId")).stream().map(BookMapper.MAPPER :: toDto).collect(Collectors.toList());
        }
        return bookDao.getAll().stream().map(BookMapper.MAPPER :: toDto).collect(Collectors.toList());
    }

    @Override
    public void update(BookDto bookDto) {
        bookDao.set(BookMapper.MAPPER.fromDto(bookDto));
    }

    @Override
    public void delete(String idBookDto) {
        bookDao.delete(idBookDto);
    }
}

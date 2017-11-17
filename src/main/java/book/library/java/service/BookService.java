package book.library.java.service;

import book.library.java.dto.BookDto;
import book.library.java.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface BookService {
    void create(BookDto bookDto) throws DaoException;
    List<BookDto> read(Map<String, String> params) throws Exception;
    void update(BookDto bookDto) throws DaoException;
    void delete(List<Integer> listIdBooks) throws DaoException;
}

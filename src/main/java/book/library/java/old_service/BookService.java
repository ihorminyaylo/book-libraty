package book.library.java.old_service;

import book.library.java.dto.BookDto;
import book.library.java.dto.BooksAndPageDto;
import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface BookService {
    void create(BookDto bookDto) throws DaoException;
    BooksAndPageDto read(ReadParamsDto readParamsDto) throws BusinessException;
    void update(BookDto bookDto) throws DaoException;
    void delete(List<Integer> listIdBooks) throws DaoException;


    List<BookDto> readOld(Map<String, String> params) throws Exception;
}

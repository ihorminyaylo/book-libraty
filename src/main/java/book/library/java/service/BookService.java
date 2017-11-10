package book.library.java.service;

import book.library.java.dto.BookDto;

import java.util.List;
import java.util.Map;

public interface BookService {
    void create(BookDto bookDto);
    List<BookDto> read(Map<String, String> params) throws Exception;
    void update(BookDto bookDto);
    void delete(List<String> listIdBooks);
}

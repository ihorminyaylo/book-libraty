package book.library.java.service;

import book.library.java.dto.BookDto;

import java.util.List;

public interface BookService {
    void create(BookDto bookDto);
    List<BookDto> read();
    void update(BookDto bookDto);
    void delete(String idBookDto);
}

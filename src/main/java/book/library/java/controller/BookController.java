package book.library.java.controller;

import book.library.java.dto.BookDto;
import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
/*
import book.library.java.old_service.BookServiceOld;
*/
import book.library.java.model.Book;
import book.library.java.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Book book) throws DaoException, BusinessException {
        bookService.create(book);
        return ResponseEntity.ok(book);
    }

    @PostMapping(value = "/find")
    public  ResponseEntity<?> read(@RequestBody ReadParamsDto readParamsDto) {
        try {
            return ResponseEntity.ok(bookService.read(readParamsDto));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/event")
    public ResponseEntity update(@RequestBody Book book) throws DaoException, BusinessException {
        bookService.update(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping(value = "/delete")
    public ResponseEntity delete(@RequestBody List<Integer> listIdBooks) throws DaoException, BusinessException {
        bookService.delete(listIdBooks);
        return ResponseEntity.ok(listIdBooks);
    }
}

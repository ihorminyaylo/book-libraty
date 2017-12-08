package book.library.java.controller;

import book.library.java.dto.BookWithAuthors;
import book.library.java.dto.ListParams;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
/*
import book.library.java.old_service.BookServiceOld;
*/
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;
import book.library.java.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody BookWithAuthors bookWithAuthors) throws DaoException, BusinessException {
        bookService.create(bookWithAuthors);
        return ResponseEntity.ok(bookWithAuthors);
    }

    @PostMapping(value = "/find")
    public ResponseEntity<?> read(@RequestBody(required = false) ListParams<BookPattern> listParams) {
        try {
            return ResponseEntity.ok(bookService.read(listParams));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Book book) throws DaoException, BusinessException {
        bookService.update(book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer idBook) throws DaoException, BusinessException {
        bookService.delete(idBook);
        return ResponseEntity.ok(idBook);
    }

    @PutMapping(value = "/delete")
    public ResponseEntity bulkDelete(@RequestBody List<Integer> listIdBooks) throws DaoException, BusinessException {
        bookService.bulkDelete(listIdBooks);
        return ResponseEntity.ok(listIdBooks);
    }
}

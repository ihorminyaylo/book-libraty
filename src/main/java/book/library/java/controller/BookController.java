package book.library.java.controller;

import book.library.java.dto.BookWithAuthors;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Book;
import book.library.java.model.ListParams;
import book.library.java.model.pattern.BookPattern;
import book.library.java.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/book")
// todo: why DaoException in methods signature?
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody BookWithAuthors bookWithAuthors) throws DaoException, BusinessException {
        return ResponseEntity.ok(bookService.create(bookWithAuthors));
    }

    @PostMapping(value = "/find")
    public ResponseEntity<?> read(@RequestBody(required = false) ListParams<BookPattern> listParams) throws DaoException, BusinessException {
        return ResponseEntity.ok(bookService.read(listParams));
    }

    @GetMapping(value = "/findTop")
    public ResponseEntity readTop(@RequestParam Integer count) {
        return ResponseEntity.ok(bookService.readTop(count));
    }

    @GetMapping(value = "/average_rating")
    @ExceptionHandler(DaoException.class) // todo: ???
    public ResponseEntity getAverageRating() throws DaoException {
        return ResponseEntity.ok(bookService.getAverageRating());
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

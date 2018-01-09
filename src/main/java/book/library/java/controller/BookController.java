package book.library.java.controller;

import book.library.java.exception.BusinessException;
import book.library.java.list.ListParams;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;
import book.library.java.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Book book) throws BusinessException {
        log.info("In create(book=[{}])", book);
        return ResponseEntity.ok(bookService.create(book));
    }

    @PostMapping(value = "/find")
    public ResponseEntity<?> read(@RequestBody(required = false) ListParams<BookPattern> listParams) throws BusinessException {
        log.info("In read(listParams=[{}])", listParams);
        return ResponseEntity.ok(bookService.readBooks(listParams));
    }

    @GetMapping(value = "/byBook")
    public ResponseEntity readByBookId(@RequestParam Integer idBook) throws BusinessException {
        log.info("In readByBookId(idBook=[{}])", idBook);
        return ResponseEntity.ok(bookService.readById(idBook));
    }

    @GetMapping(value = "/findTop")
    public ResponseEntity readTopFive() {
        log.info("In readTopFive()");
        return ResponseEntity.ok(bookService.readTopFive());
    }

    @GetMapping(value = "/count_books_each_rating")
    public ResponseEntity readCountOfEachRating() {
        log.info("In readCountOfEachRating()");
        return ResponseEntity.ok(bookService.getCountOfEachRating());
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Book book) throws BusinessException {
        log.info("In update(book=[{}])", book);
        bookService.update(book);
        return ResponseEntity.ok(book.getId());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer idBook) throws BusinessException {
        log.info("In delete(idBook=[{}])", idBook);
        bookService.delete(idBook);
        return ResponseEntity.ok(idBook);
    }

    @PutMapping(value = "/delete")
    public ResponseEntity bulkDelete(@RequestBody List<Integer> listIdBooks) throws BusinessException {
        log.info("In bulkDelete(listIdBooks=[{}])", listIdBooks);
        bookService.bulkDelete(listIdBooks);
        return ResponseEntity.ok(listIdBooks);
    }
}

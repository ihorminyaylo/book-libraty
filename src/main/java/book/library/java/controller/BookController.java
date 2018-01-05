package book.library.java.controller;

import book.library.java.exception.BusinessException;
import book.library.java.list.ListParams;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;
import book.library.java.service.BookService;
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

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Book book) throws BusinessException {
        return ResponseEntity.ok(bookService.create(book));
    }

    @GetMapping(value = "/byBook")
    public ResponseEntity readByBookId(@RequestParam Integer idBook) throws BusinessException {
        return ResponseEntity.ok(bookService.readByBookId(idBook));
    }

    @PostMapping(value = "/find")
    public ResponseEntity<?> read(@RequestBody(required = false) ListParams<BookPattern> listParams) throws BusinessException {
        return ResponseEntity.ok(bookService.readBooks(listParams));
    }

    @GetMapping(value = "/findTop")
    public ResponseEntity readTopFive() {
        return ResponseEntity.ok(bookService.readTopFive());
    }

    @GetMapping(value = "/count_books_each_rating")
    public ResponseEntity readDetail() {
        return ResponseEntity.ok(bookService.getCountOfEachRating());
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Book book) throws BusinessException {
        bookService.updateBook(book);
        return ResponseEntity.ok(book.getId());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer idBook) throws BusinessException {
        bookService.delete(idBook);
        return ResponseEntity.ok(idBook);
    }

    @PutMapping(value = "/delete")
    public ResponseEntity bulkDelete(@RequestBody List<Integer> listIdBooks) throws BusinessException {
        bookService.bulkDelete(listIdBooks);
        return ResponseEntity.ok(listIdBooks);
    }
}

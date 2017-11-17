package book.library.java.controller;

import book.library.java.dto.BookDto;
import book.library.java.exception.DaoException;
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
    public void create(@RequestBody BookDto bookDto) throws DaoException {
        bookService.create(bookDto);
    }

    @GetMapping(value = "/find")
    public  ResponseEntity<List<BookDto>> read(
            @RequestParam(value = "byAuthor", required = false) String authorId,
            @RequestParam(value = "byRating", required = false) String rating) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("authorId", authorId);
        params.put("rating", rating);
        return ResponseEntity.ok(bookService.read(params));
    }

    @PutMapping(value = "/event")
    public void update(@RequestBody BookDto bookDto) throws DaoException {
        bookService.update(bookDto);
    }

    @PutMapping(value = "/delete")
    public void delete(@RequestBody List<Integer> listIdBooks) throws DaoException {
        bookService.delete(listIdBooks);
    }
}

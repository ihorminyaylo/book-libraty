package book.library.java.rest;

import book.library.java.dto.BookDto;
import book.library.java.model.Book;
import book.library.java.service.BookService;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/book")
public class BookRest {

    private final BookService bookService;

    @Autowired
    public BookRest(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public void create(@RequestBody BookDto bookDto) {
        bookService.create(bookDto);
    }

    @GetMapping(value = "/find")
    public  ResponseEntity<List<BookDto>> read(
            @RequestParam(value = "byAuthor", required = false) String authorId) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("authorId", authorId);
        return ResponseEntity.ok(bookService.read(params));
    }

    @PutMapping(value = "/event")
    public void update(@RequestBody BookDto bookDto) {
        bookService.update(bookDto);
    }

    @PutMapping(value = "/delete")
    public void delete(@RequestBody String idBook) {
        bookService.delete(idBook);
    }
}

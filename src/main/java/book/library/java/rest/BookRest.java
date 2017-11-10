package book.library.java.rest;

import book.library.java.dto.BookDto;
import book.library.java.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(value = "byAuthor", required = false) String authorId,
            @RequestParam(value = "byRating", required = false) String rating) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("authorId", authorId);
        params.put("rating", rating);
        return ResponseEntity.ok(bookService.read(params));
    }

    @PutMapping(value = "/event")
    public void update(@RequestBody BookDto bookDto) {
        bookService.update(bookDto);
    }

    @PutMapping(value = "/delete")
    public void delete(@RequestBody List<String> listIdBooks) {
        bookService.delete(listIdBooks);
    }
}

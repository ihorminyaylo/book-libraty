package book.library.java.controller;

import book.library.java.dto.AuthorsAndPageDto;
import book.library.java.dto.AuthorDto;
import book.library.java.exception.DaoException;
import book.library.java.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public void create(@RequestBody AuthorDto authorDto) throws DaoException {
        authorService.create(authorDto);
    }

    @GetMapping(value = "/{id}")
    public  ResponseEntity<List<AuthorDto>> get(){
        return null;
    }

    @GetMapping(value = "/find")
    public  ResponseEntity<AuthorsAndPageDto> read(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam(value = "byAverageRating", required = false) String answer
    ) {
        return ResponseEntity.ok(authorService.read(page, pageSize));
        //Map<String, String> params = new HashMap<>();
        //params.put("answer", answer);
        //return ResponseEntity.ok(authorService.read(params));
    }

    @PutMapping(value = "/event")
    public void update(@RequestBody AuthorDto authorDto) throws DaoException {
        authorService.update(authorDto);
    }

    @PutMapping(value = "/delete")
    public void delete(@RequestBody Integer idAuthor) throws DaoException {
        authorService.delete(idAuthor);
    }
}

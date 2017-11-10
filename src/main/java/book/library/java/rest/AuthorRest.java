package book.library.java.rest;

import book.library.java.dto.AuthorDto;
import book.library.java.model.Author;
import book.library.java.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/author")
public class AuthorRest {

    private final AuthorService authorService;

    @Autowired
    public AuthorRest(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public void create(@RequestBody AuthorDto authorDto) {
        authorService.create(authorDto);
    }

    @GetMapping(value = "/find")
    public  ResponseEntity<List<AuthorDto>> read() {
        return ResponseEntity.ok(authorService.read());
    }

    @PutMapping(value = "/event")
    public void update(@RequestBody AuthorDto authorDto) {
        authorService.update(authorDto);
    }

    @PutMapping(value = "/delete")
    public void delete(@RequestBody String idAuthor) {
        authorService.delete(idAuthor);
    }
}

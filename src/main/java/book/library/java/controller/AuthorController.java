package book.library.java.controller;

import book.library.java.dto.AuthorDto;
import book.library.java.model.Author;
import book.library.java.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public void create(@RequestBody AuthorDto authorDto) {
        authorService.create(authorDto);
    }

    @GetMapping(value = "/{id}")
    public  ResponseEntity<List<AuthorDto>> get(){
        return null;
    }

    @GetMapping(value = "/find")
    public  ResponseEntity<List<AuthorDto>> read(
            @RequestParam(value = "byAverageRating", required = false) String answer
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("answer", answer);
        return ResponseEntity.ok(authorService.read(params));
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

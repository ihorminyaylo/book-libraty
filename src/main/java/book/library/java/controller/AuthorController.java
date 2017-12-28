package book.library.java.controller;

import book.library.java.exception.BusinessException;
import book.library.java.model.Author;
import book.library.java.list.ListParams;
import book.library.java.model.pattern.AuthorPattern;
import book.library.java.service.AuthorService;
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
@RequestMapping(value = "/api/author")
public class AuthorController {
    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Author author) throws BusinessException {
        return ResponseEntity.ok(authorService.create(author));
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<?> readAll() {
        return ResponseEntity.ok(authorService.readAll());
    }

    @GetMapping(value = "/findTop")
    public ResponseEntity readTopFive() throws BusinessException {
        return ResponseEntity.ok(authorService.readTopFive());
    }
    @PostMapping(value = "/find")
    public ResponseEntity read(@RequestBody ListParams<AuthorPattern> listParams) throws BusinessException {
        return ResponseEntity.ok(authorService.read(listParams));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Author author) throws BusinessException {
        authorService.update(author);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer idAuthor) throws BusinessException {
        return ResponseEntity.ok(authorService.deleteAuthor(idAuthor));
    }

    @PutMapping(value = "/delete")
    public ResponseEntity bulkDelete(@RequestBody List<Integer> idEntities) throws BusinessException {
        return ResponseEntity.ok(authorService.bulkDelete(idEntities));
    }
}

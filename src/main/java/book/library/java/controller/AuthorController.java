package book.library.java.controller;

import book.library.java.dto.ListParams;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;
import book.library.java.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Author author) throws DaoException, BusinessException {
        try {
            authorService.create(author);
            return ResponseEntity.ok(author);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping(value = "/findAll")
    public ResponseEntity<?> readAll() {
        return ResponseEntity.ok(authorService.readAll());
    }

    @PostMapping(value = "/find")
    public ResponseEntity<?> read(@RequestBody ListParams<AuthorPattern> listParams) {
        try {
            return ResponseEntity.ok(authorService.read(listParams));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/byBook")
    public ResponseEntity<?> readByBook(@RequestParam Integer idBook) {
        try {
            return ResponseEntity.ok(authorService.readByBook(idBook));
        } catch (BusinessException e) {
            return null;// ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Author author) throws BusinessException {
        try {
            authorService.update(author);
            return ResponseEntity.ok(author);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /*//todo: in progress
    @PutMapping(value = "/delete")
    public ResponseEntity bulkDelete(@RequestBody List<Integer> idEntities) throws BusinessException {
        try {
            authorService.bulkDelete(idEntities);
            return ResponseEntity.ok(idEntities);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/
}

package book.library.java.controller;

import book.library.java.exception.BusinessException;
import book.library.java.list.ListParams;
import book.library.java.model.Author;
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

    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Author author) throws BusinessException {
        log.info("In create(author=[{}])", author);
        return ResponseEntity.ok(authorService.create(author));
    }

    @PostMapping(value = "/find")
    public ResponseEntity read(@RequestBody ListParams<AuthorPattern> listParams) throws BusinessException {
        log.info("In read(listParams=[{}])", listParams);
        return ResponseEntity.ok(authorService.read(listParams));
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<?> readAll() {
        log.info("In readAll()");
        return ResponseEntity.ok(authorService.readAll());
    }

    @GetMapping(value = "/byId")
    public ResponseEntity readByBookId(@RequestParam Integer idAuthor) throws BusinessException {
        log.info("In readByBookId(idAuthor=[{}])", idAuthor);
        return ResponseEntity.ok(authorService.readById(idAuthor));
    }

    @GetMapping(value = "/findTop")
    public ResponseEntity readTopFive() throws BusinessException {
        log.info("In readTopFive()");
        return ResponseEntity.ok(authorService.readTopFive());
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Author author) throws BusinessException {
        log.info("In update(author=[{}])", author);
        authorService.update(author);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer idAuthor) throws BusinessException {
        log.info("In delete(idAuthor=[{}])", idAuthor);
        return ResponseEntity.ok(authorService.delete(idAuthor));
    }

    @PutMapping(value = "/delete")
    public ResponseEntity bulkDelete(@RequestBody List<Integer> idEntities) throws BusinessException {
        log.info("In bulkDelete(idEntities=[{}])", idEntities);
        return ResponseEntity.ok(authorService.bulkDelete(idEntities));
    }
}

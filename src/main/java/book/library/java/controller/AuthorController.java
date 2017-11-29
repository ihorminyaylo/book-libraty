package book.library.java.controller;

import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
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

    //create
    @PostMapping
    public ResponseEntity create(@RequestBody Author author) throws DaoException, BusinessException {
        try {
            authorService.create(author);
            return ResponseEntity.ok(author);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //read
    @PostMapping(value = "/find")
    public  ResponseEntity<?> read(@RequestBody ReadParamsDto readParamsDto) {
        try {
            return ResponseEntity.ok(authorService.read(readParamsDto));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //update
    @PutMapping(value = "/event")
    public ResponseEntity update(@RequestBody Author author) throws BusinessException {
        try {
            authorService.update(author);
            return ResponseEntity.ok(author);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //todo: in progress
    //delete
    @PutMapping(value = "/delete")
    public ResponseEntity delete(@RequestBody List<Integer> idEntities) throws BusinessException {
        try {
            return ResponseEntity.ok(authorService.delete(idEntities));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

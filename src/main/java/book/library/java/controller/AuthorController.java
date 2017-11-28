package book.library.java.controller;

import book.library.java.dto.ReadParamsDto;
import book.library.java.dto.AuthorDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.mapper.AuthorMapper;
import book.library.java.old_service.AuthorService;
import book.library.java.service.AuthorService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/author")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorService2 authorService2;

    @Autowired
    public AuthorController(AuthorService authorService, AuthorService2 authorService2) {
        this.authorService = authorService;
        this.authorService2 = authorService2;
    }

    //create
    @PostMapping
    public ResponseEntity create(@RequestBody AuthorDto authorDto) throws DaoException, BusinessException {
        try {
            authorService.create(AuthorMapper.MAPPER.fromDto(authorDto));
            return ResponseEntity.ok(authorDto);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //read_new
    @PostMapping(value = "/find2")
    public  ResponseEntity<?> read2(@RequestBody ReadParamsDto readParamsDto) {
        try {
            return ResponseEntity.ok(authorService2.read(readParamsDto));
        } catch (BusinessException e) {
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
    public void update(@RequestBody AuthorDto authorDto) throws DaoException {
        authorService.update(authorDto);
    }

    //delete
    @PutMapping(value = "/delete")
    public void delete(@RequestBody Integer idAuthor) throws DaoException {
        authorService.delete(idAuthor);
    }







    //todo: remove

    /*@GetMapping(value = "/{id}")
    public  ResponseEntity<List<AuthorDto>> get(){
        return null;
    }

    @GetMapping(value = "/find-old")
    public  ResponseEntity<?> readOld(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam(value = "byAverageRating", required = false) String answer
    ) throws BusinessException {
        try {
            return ResponseEntity.ok(authorService.readOld(page, pageSize));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        //Map<String, String> params = new HashMap<>();
        //params.put("answer", answer);
        //return ResponseEntity.ok(authorService.readOld(params));
    }*/
}

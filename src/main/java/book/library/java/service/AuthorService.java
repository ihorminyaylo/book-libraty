package book.library.java.service;

import book.library.java.dto.AuthorDto;


import java.util.List;

public interface AuthorService {
    void create(AuthorDto authorDto);
    List<AuthorDto> read();
    void update(AuthorDto authorDto);
    void delete(String idAuthorDto);
}

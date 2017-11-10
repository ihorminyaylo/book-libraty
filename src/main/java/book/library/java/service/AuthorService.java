package book.library.java.service;

import book.library.java.dto.AuthorDto;


import java.util.List;
import java.util.Map;

public interface AuthorService {
    void create(AuthorDto authorDto);
    List<AuthorDto> read(Map<String, String> params);
    void update(AuthorDto authorDto);
    void delete(String idAuthorDto);
}

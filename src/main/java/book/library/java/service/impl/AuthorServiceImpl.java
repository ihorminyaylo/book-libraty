package book.library.java.service.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.dto.AuthorDto;
import book.library.java.mapper.AuthorMapper;
import book.library.java.mapper.BookMapper;
import book.library.java.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public void create(AuthorDto authorDto) {
        this.authorDao.add(AuthorMapper.MAPPER.fromDto(authorDto));
    }

    @Override
    public List<AuthorDto> read(Map<String, String> params) {
        if (params.get("answer").equals("yes")) {
            return authorDao.getByAverageRating().stream().map(AuthorMapper.MAPPER :: toDto).collect(Collectors.toList());
        }
        return authorDao.getAll().stream().map(AuthorMapper.MAPPER :: toDto).collect(Collectors.toList());
    }

    @Override
    public void update(AuthorDto authorDto) {
        authorDao.set(AuthorMapper.MAPPER.fromDto(authorDto));
    }

    @Override
    public void delete(String idAuthorDto) {
        authorDao.delete(idAuthorDto);
    }
}

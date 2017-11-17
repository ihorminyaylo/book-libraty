package book.library.java.service.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.dao.ReviewDao;
import book.library.java.dto.AuthorsAndPageDto;
import book.library.java.dto.AuthorDto;
import book.library.java.exception.DaoException;
import book.library.java.mapper.AuthorMapper;
import book.library.java.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final ReviewDao reviewDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao, ReviewDao reviewDao) {
        this.authorDao = authorDao;
        this.reviewDao = reviewDao;
    }

    @Override
    public void create(AuthorDto authorDto) throws DaoException {
        this.authorDao.create(AuthorMapper.MAPPER.fromDto(authorDto));
    }

    @Override
    public List<AuthorDto> read(Map<String, String> params) {
        if (params.get("answer") != null && params.get("answer").equals("search")) {
            return authorDao.getByAverageRating().stream().map(AuthorMapper.MAPPER::toDto).collect(Collectors.toList());
        }
        return authorDao.findAll().stream().map(AuthorMapper.MAPPER :: toDto).collect(Collectors.toList());
    }

    @Override
    public void update(AuthorDto authorDto) throws DaoException {
        authorDao.update(AuthorMapper.MAPPER.fromDto(authorDto));
    }

    @Override
    public void delete(Integer idAuthorDto) throws DaoException {
        authorDao.delete(idAuthorDto);
    }

    @Override
    public AuthorsAndPageDto read(Integer page, Integer pageSize) {
        if (page != 1) {
            page = (page - 1)* pageSize + 1;
        }
        List<AuthorDto> authorDtoList = authorDao.findWithPagination(page, pageSize).stream().map(AuthorMapper.MAPPER :: toDto).collect(Collectors.toList());
        BigInteger totalRecords = authorDao.totalRecords();
        BigInteger pages = (totalRecords.add(BigInteger.valueOf(pageSize)).subtract(BigInteger.valueOf(1))).divide(BigInteger.valueOf(pageSize));
        return new AuthorsAndPageDto(authorDtoList, pages);
    }
}

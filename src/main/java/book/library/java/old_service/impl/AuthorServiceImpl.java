package book.library.java.old_service.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.dao.ReviewDao;
import book.library.java.dto.ReadParamsDto;
import book.library.java.dto.AuthorsAndPageDto;
import book.library.java.dto.AuthorDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.mapper.AuthorMapper;
import book.library.java.model.Author;
import book.library.java.old_service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final String NAME = "^[A-Z][a-z]+";
    private final Pattern PATTERN_NAME = Pattern.compile(NAME);
    private final AuthorDao authorDao;
    private final ReviewDao reviewDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao, ReviewDao reviewDao) {
        this.authorDao = authorDao;
        this.reviewDao = reviewDao;
    }

    @Override
    public void create(Author author) throws BusinessException {
        Matcher matcherFirstName = PATTERN_NAME.matcher(author.getFirstName());
        if (!matcherFirstName.matches()) {
            throw new BusinessException("First name isn't correct");
        }
        Matcher matcherLastName = PATTERN_NAME.matcher(author.getSecondName());
        if (!matcherLastName.matches()) {
            throw new BusinessException("Last name isn't correct");
        }
        try {
            this.authorDao.create(author);
        } catch (DaoException e) {
//            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional
    public AuthorsAndPageDto read(ReadParamsDto readParamsDto) throws BusinessException {
        List<AuthorDto> authorDtoList;
        Integer totalItems = authorDao.totalRecords();
        if (readParamsDto.getLimit() == null || readParamsDto.getOffset() != null) {
            authorDtoList = authorDao.find(readParamsDto).stream().map(AuthorMapper.MAPPER :: toDto).collect(Collectors.toList());
        }
        else {
            authorDtoList = authorDao.findAll().stream().map(AuthorMapper.MAPPER :: toDto).collect(Collectors.toList());
        }
        return new AuthorsAndPageDto(authorDtoList, totalItems);
    }

    @Override
    public void update(AuthorDto authorDto) throws DaoException {
        authorDao.update(AuthorMapper.MAPPER.fromDto(authorDto));
    }

    @Override
    public void delete(Integer idAuthorDto) throws DaoException {
        authorDao.delete(idAuthorDto);
    }








    // todo: remove

    /*@Override
    public AuthorsAndPageDto readOld(Integer page, Integer pageSize) throws BusinessException {
        BigInteger totalRecords = authorDao.totalRecords();
        BigInteger pages = (totalRecords.add(BigInteger.valueOf(pageSize)).subtract(BigInteger.valueOf(1))).divide(BigInteger.valueOf(pageSize));
        int correctPage = BigInteger.valueOf(page).compareTo(pages);
        if (page < 0 || correctPage == 1) {
            throw new BusinessException("Page can't be less 0 and more '"+pages+"'");
        }
        if (page != 1) {
            page = (page - 1)* pageSize + 1;
        }
        List<AuthorDto> authorDtoList = authorDao.find(page, pageSize).stream().map(AuthorMapper.MAPPER :: toDto).collect(Collectors.toList());
        return new AuthorsAndPageDto(authorDtoList, pages);
    }
    @Override
    public List<AuthorDto> readOld(Map<String, String> params) {
        if (params.get("answer") != null && params.get("answer").equals("search")) {
            return authorDao.getByAverageRating().stream().map(AuthorMapper.MAPPER::toDto).collect(Collectors.toList());
        }
        return authorDao.findAll().stream().map(AuthorMapper.MAPPER :: toDto).collect(Collectors.toList());
    }*/
}

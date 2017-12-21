package book.library.java.service.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.dao.impl.AuthorDaoImpl;
import book.library.java.dto.AuthorDto;
import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.list.ListParams;
import book.library.java.model.pattern.AuthorPattern;
import book.library.java.service.AuthorService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class AuthorServiceImpl extends AbstractServiceImpl<Author, AuthorPattern> implements AuthorService {
    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDaoImpl authorDao) {
        super(authorDao);
        this.authorDao =  authorDao;
    }

    @Override
    public List<Author> readAll() {
        return authorDao.findAll();
    }

    @Override
    public List<AuthorDto> readTopFive() throws BusinessException {
        try {
            return authorDao.readTopFive().stream().map(author -> new AuthorDto(author)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void update(Author author) throws BusinessException {
        validateEntity(author);
        try {
            authorDao.update(author);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<AuthorDto> bulkDelete(List<Integer> idAuthors) throws BusinessException {
        try {
            return authorDao.bulkDelete(idAuthors).stream().map(author -> new AuthorDto(author)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public AuthorDto deleteAuthor(Integer idAuthor) throws BusinessException {
        try {
            Author author = authorDao.deleteAuthor(idAuthor);
            if (author != null) {
                return new AuthorDto(author);
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
        return null;
    }

    @Override
    public void validateEntity(Author author) throws BusinessException {
        if (StringUtils.isBlank(author.getFirstName())) {
            throw new BusinessException("First name isn't correct");
        }
    }
}

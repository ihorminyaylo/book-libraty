package book.library.java.service.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.dto.AuthorDto;
import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.list.ListParams;
import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;
import book.library.java.service.AuthorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl extends AbstractService<Author, AuthorPattern> implements AuthorService {

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        super(authorDao);
    }


    @Override
    public ListEntityPage<Author> read(ListParams<AuthorPattern> listParams) throws BusinessException {
        List<Author> listEntity = new ArrayList<>();
        Integer totalItems = getDao().totalRecords(listParams);
        if (listParams.getLimit() != null || listParams.getOffset() != null) {
            try {
                listEntity = getDao().find(listParams);
                listEntity.forEach(author -> author.setAverageRating(author.getAverageRating().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
            } catch (Exception e) {
                throw new BusinessException(e);
            }
        }
        return new ListEntityPage<>(listEntity, totalItems);
    }

    @Override
    public List<Author> readAll() {
        return getDao().findAll();
    }

    @Override
    public List<AuthorDto> readTopFive() throws BusinessException {
        try {
            List<Author> authors = ((AuthorDao) getDao()).readTopFive();
            authors.forEach(author -> author.setAverageRating(author.getAverageRating().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
            return authors.stream().map(AuthorDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public AuthorDto readById(Integer idAuthor) throws BusinessException {
        try {
            return new AuthorDto(getDao().get(idAuthor));
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void update(Author author) throws BusinessException {
        validateEntity(author);
        try {
            getDao().update(author);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public List<AuthorDto> bulkDelete(List<Integer> idAuthors) throws BusinessException {
        try {
            return ((AuthorDao) getDao()).bulkDelete(idAuthors).stream().map(AuthorDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public AuthorDto deleteAuthor(Integer idAuthor) throws BusinessException {
        try {
            Author author = ((AuthorDao) getDao()).deleteAuthor(idAuthor);
            if (author != null) {
                return new AuthorDto(author);
            }
        } catch (Exception e) {
            throw new BusinessException(e);
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

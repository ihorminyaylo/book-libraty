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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl extends AbstractService<AuthorDao, Author, AuthorPattern, AuthorDto> implements AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        super(authorDao);
    }


    @Override
    public ListEntityPage<Author> read(ListParams<AuthorPattern> listParams) throws BusinessException {
        List<Author> listEntity = new ArrayList<>();
        Integer totalItems = null;
        try {
            totalItems = getDao().totalRecords(listParams);
        } catch (DaoException e) {
            log.error("in read() exception - [{}] ", e);
            throw new BusinessException(e);
        }
        if (listParams.getLimit() != null || listParams.getOffset() != null) {
            try {
                listEntity = getDao().find(listParams);
                listEntity.forEach(author -> author.setAverageRating(author.getAverageRating().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
            } catch (Exception e) {
                log.error("in read() exception - [{}] ", e);
                throw new BusinessException(e);
            }
        }
        return new ListEntityPage<>(listEntity, totalItems);
    }

    @Override
    public List<AuthorDto> readTopFive() throws BusinessException {
        try {
            List<Author> authors = getDao().findTopFive();
            authors.forEach(author -> author.setAverageRating(author.getAverageRating().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
            return authors.stream().map(AuthorDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("in readTopFive() exception - [{}] ", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public void validateEntity(Author author) throws BusinessException {
        if (StringUtils.isBlank(author.getFirstName())) {
            log.error("in validateEntity() exception - First name isn't correct");
            throw new BusinessException("First name isn't correct");
        }
    }
}

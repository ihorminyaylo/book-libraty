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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl extends AbstractService<AuthorDao, Author, AuthorPattern, AuthorDto> implements AuthorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        super(authorDao);
    }


    @Override
    public ListEntityPage<AuthorDto> readAuthor(ListParams<AuthorPattern> listParams) throws BusinessException {
        List<Author> listEntity = new ArrayList<>();
        Integer totalItems;
        try {
            totalItems = getDao().totalRecords(listParams);
        } catch (DaoException e) {
            LOGGER.error("in read() exception!", e);
            throw new BusinessException(e);
        }
        if (listParams.getLimit() != null || listParams.getOffset() != null) {
            try {
                listEntity = getDao().find(listParams);
            } catch (Exception e) {
                LOGGER.error("in read() exception!", e);
                throw new BusinessException(e);
            }
        }
        return new ListEntityPage<>(listEntity.stream().map(AuthorDto::new).collect(Collectors.toList()), totalItems);
    }

    @Override
    public List<AuthorDto> readTopFive() throws BusinessException {
        try {
            List<Author> authors = getDao().findTopFive();
            return authors.stream().map(AuthorDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("in readTopFive() exception!", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public void validateEntity(Author author) throws BusinessException {
        if (StringUtils.isBlank(author.getFirstName())) {
            LOGGER.error("in validateEntity() exception!");
            throw new BusinessException("First name isn't correct");
        }
    }
}

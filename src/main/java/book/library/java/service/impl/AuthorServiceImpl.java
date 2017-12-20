package book.library.java.service.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.dao.BookDao;
import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dto.AuthorDto;
import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.list.ListParams;
import book.library.java.model.pattern.AuthorPattern;
import book.library.java.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class AuthorServiceImpl extends AbstractServiceImpl<Author, AuthorPattern> implements AuthorService {
    private final AuthorDao authorDao;

    @Autowired
    // todo: why: @Qualifier("authorDaoImpl") AbstractDaoImpl<Author, AuthorPattern> entityDaoType ??
    public AuthorServiceImpl(@Qualifier("authorDaoImpl") AbstractDaoImpl<Author, AuthorPattern> entityDaoType) {
        super(entityDaoType);
        authorDao = (AuthorDao) entityDaoType;
    }

    @Override
    public Integer create(Author author) throws BusinessException {
        validateAuthor(author);
        try {
            return authorDao.create(author);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public ListEntityPage<Author> read(ListParams<AuthorPattern> listParams) throws BusinessException {
        ListEntityPage<Author> listEntityPage;
	    // todo: very strange condition and behaviour ???
        if (listParams.getPattern() != null && listParams.getPattern().toString().contains("byId")) { // todo: WTF?
            Author author = null; // todo: WTF?
            try {
                author = authorDao.get(Integer.parseInt(listParams.getPattern().toString().substring(5)));
            } catch (Exception e) {
                throw new BusinessException(e.getMessage(), e.getCause());
            }
            List<Author> authors = new ArrayList<>();
            authors.add(author);
            listEntityPage = new ListEntityPage<>(authors, authorDao.totalRecords(null));
        } else {
            listEntityPage = super.read(listParams);
        }
        //listEntityPage.getList().forEach(author -> author.setBooks(null)); // todo: WTF?
        return listEntityPage;
    }

    @Override
    public List<Author> readAll() {
        return authorDao.findAll();
    }

    @Override
    public List<AuthorDto> readTop(Integer count) throws BusinessException {
        try {
            return authorDao.readTop(count).stream().map(author -> new AuthorDto(author)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void update(Author author) throws BusinessException {
        validateAuthor(author);
        try {
            authorDao.update(author);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<AuthorDto> bulkDelete(List<Integer> idAuthors) throws BusinessException {
        try {
            return authorDao.bulkDeleteAuthors(idAuthors).stream().map(author -> new AuthorDto(author)).collect(Collectors.toList());
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

    private void validateAuthor(Author author) throws BusinessException { // todo: Method 'validateAuthor()' may be 'static'
        if (author.getFirstName() == null || author.getFirstName().isEmpty()) { // todo: use StringUtils
            throw new BusinessException("First name isn't correct");
        }
        if (author.getFirstName() == null || author.getFirstName().isEmpty()) { // todo: use StringUtils
            throw new BusinessException("Last name isn't correct");
        }
    }
}

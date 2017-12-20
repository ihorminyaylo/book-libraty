package book.library.java.service.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.dao.BookDao;
import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dto.AuthorDto;
import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.ListParams;
import book.library.java.model.pattern.AuthorPattern;
import book.library.java.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class AuthorServiceImpl extends AbstractServiceImpl<Author, AuthorPattern> implements AuthorService {
    private final AuthorDao authorDao; // todo: useless - remove
    private final BookDao bookDao; // todo: useless - remove

    @Autowired
    // todo: why: @Qualifier("authorDaoImpl") AbstractDaoImpl<Author, AuthorPattern> entityDaoType ??
    public AuthorServiceImpl(@Qualifier("authorDaoImpl") AbstractDaoImpl<Author, AuthorPattern> entityDaoType, BookDao bookDao) {
        super(entityDaoType);
        authorDao = (AuthorDao) entityDaoType;
        this.bookDao = bookDao;
    }

    @Override
    public Integer create(Author author) throws BusinessException, DaoException { // todo: why DaoException in signature?
        validateAuthor(author);
        return authorDao.create(author);
    }

    @Override
    public EntitiesAndPageDto<Author> read(ListParams listParams) throws BusinessException, DaoException { // todo: generic for ListParams!, why DaoException in signature?
        EntitiesAndPageDto<Author> entitiesAndPageDto;
	    // todo: very strange condition and behaviour ???
        if (listParams.getPattern() != null && listParams.getPattern().toString().contains("byId")) { // todo: WTF?
            Author author = authorDao.get(Integer.parseInt(listParams.getPattern().toString().substring(5))); // todo: WTF?
            List<Author> authors = new ArrayList<>();
            authors.add(author);
            entitiesAndPageDto = new EntitiesAndPageDto<>(authors, authorDao.totalRecords(null));
        } else {
            entitiesAndPageDto = super.read(listParams);
        }
        entitiesAndPageDto.getList().forEach(author -> author.setBooks(null)); // todo: WTF?
        return entitiesAndPageDto;
    }

    @Override
    public List<Author> readAll() {
        List<Author> authors = authorDao.findAll(); // todo: redundant variable
        return authors;
    }

    @Override
    public List<AuthorDto> readTop(Integer count) throws DaoException { // todo: why DaoException in signature?
        List<AuthorDto> authorDtos = new ArrayList<>(); // todo: redundant variable
        authorDao.readTop(count).forEach(author -> { // todo: stream + map
            if (author != null) {
                authorDtos.add(new AuthorDto(author.getId(), author.getFirstName(), author.getSecondName(), author.getAverageRating().toString()));
            }
        });
        return authorDtos;
    }

    @Override
    public void update(Author author) throws BusinessException, DaoException { // todo: why DaoException in signature?
        validateAuthor(author);
        authorDao.update(author);
    }

    @Override
    public List<AuthorDto> bulkDelete(List<Integer> idAuthors) throws BusinessException, DaoException { // todo: why DaoException in signature?
        List<AuthorDto> authorDtos = new ArrayList<>(); // todo: redundant variable
        authorDao.bulkDeleteAuthors(idAuthors).forEach(author -> { // todo: stream + map
            if (author != null) {
                authorDtos.add(new AuthorDto(author.getId(), author.getFirstName(), author.getSecondName(), author.getAverageRating().toString()));
            }
        });
        return authorDtos;
    }

    @Override
    public AuthorDto deleteAuthor(Integer idAuthor) throws BusinessException {
        try {
            Author author = authorDao.deleteAuthor(idAuthor);
            if (author != null) {
                return new AuthorDto(author.getId(), author.getFirstName(), author.getSecondName(), author.getAverageRating().toString());
            }
            return null;
        } catch (Exception e) {
            throw new BusinessException(); // todo: must add base exception into BusinessException
        }
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

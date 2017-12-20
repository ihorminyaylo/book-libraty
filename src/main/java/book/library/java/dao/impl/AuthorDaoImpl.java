package book.library.java.dao.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.Book;
import book.library.java.model.ListParams;
import book.library.java.model.pattern.AuthorPattern;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorDaoImpl extends AbstractDaoImpl<Author, AuthorPattern> implements AuthorDao {

    @Override
    public List<Author> find(ListParams<AuthorPattern> listParams) throws DaoException {
        StringBuilder startQuery = new StringBuilder("SELECT * FROM  author");
        StringBuilder query = new StringBuilder(generateQueryWithParams(listParams, startQuery)); // todo: for what are you create new object of StringBuilder ?
        if (listParams.getSortParams() != null && listParams.getSortParams().getParameter() != null && listParams.getSortParams().getType() != null) { // todo: why this check here?
            generateQueryWithSortParams(listParams, query);
        } else {
            query.append(" ORDER BY average_rating, create_date");
        }
        Query nativeQuery = (Query) entityManager.createNativeQuery(query.toString(), Author.class);
        nativeQuery = setParameters(listParams, nativeQuery, "find");
        List<Author> authorList = nativeQuery.getResultList(); // todo: redundant variable
        return authorList;
    }

    private StringBuilder generateQueryWithParams(ListParams<AuthorPattern> listParams, StringBuilder query) {
        AuthorPattern pattern = listParams != null ? listParams.getPattern() : null;
        return query; // todo: ???
    }

    private Query setParameters(ListParams<AuthorPattern> listParams, Query nativeQuery, String type) { // todo: for what "type" ?
        AuthorPattern pattern = listParams != null ? listParams.getPattern() : null;
        if ("find".equals(type)) {
            if (listParams != null && listParams.getLimit() != null && listParams.getOffset() != null) {
                nativeQuery.setFirstResult(listParams.getOffset()).setMaxResults(listParams.getLimit());
            }
        }
        if (pattern != null) { // todo: ???
        }
        return nativeQuery;
    }

    @Override
    public Integer totalRecords(ListParams<AuthorPattern> listParams) {
        StringBuilder query = new StringBuilder("SELECT Count(author.id) FROM author");
        Query nativeQuery = (Query) entityManager.createNativeQuery(generateQueryWithParams(listParams, query).toString());
        nativeQuery = setParameters(listParams, nativeQuery, "totalRecords");
        BigInteger bigInteger = (BigInteger) nativeQuery.getSingleResult(); // todo: wrong implementation! Rework!
        return bigInteger.intValue();
    }

    @Override
    public List<Author> readTop(Integer count) throws DaoException { // todo: why Integer ?
        if (count == null) {
            throw new DaoException();
        }
        // todo: "ORDER BY average_rating" - is it really top authors?
        return entityManager.createNativeQuery("SELECT * FROM author ORDER BY average_rating", Author.class).setFirstResult(0).setMaxResults(count).getResultList();
    }

    @Override
    public Author deleteAuthor(Integer idAuthor) throws DaoException {
        if (idAuthor == null) {
            throw new DaoException("Entity id can't be null");
        }
        Author author = get(idAuthor);
        // todo: for what this query???!!!
        author.setBooks(entityManager.createNativeQuery("SELECT * FROM book JOIN author_book ON book.id = author_book.book_id WHERE author_id = :authorId", Book.class).setParameter("authorId", author.getId()).getResultList());  // todo: too long string!
        if (!author.getBooks().isEmpty()) {
        	// todo: WTF?!
            author.getBooks().forEach(book -> book.getAuthors());
            return author;
        }
        try {
        	// todo: why are you don't use method delete(...) ?
            entityManager.createNativeQuery("DELETE FROM author WHERE id = :id").setParameter("id", author.getId()).executeUpdate();
        } catch (Exception e) {
            throw new DaoException();
        }
        return null;
    }

    @Override
    public List<Author> bulkDeleteAuthors(List<Integer> idEntities) throws DaoException {
        List<Author> notRemove = new ArrayList<>();
        for (Integer entityId : idEntities) {
            Author author = deleteAuthor(entityId);
            if (author != null) ; // todo: WTF?!
            notRemove.add(author); // todo: what if author == null?
        }
        return notRemove;
    }
}

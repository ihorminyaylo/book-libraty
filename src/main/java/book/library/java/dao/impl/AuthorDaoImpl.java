package book.library.java.dao.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.Book;
import book.library.java.list.ListParams;
import book.library.java.model.pattern.AuthorPattern;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorDaoImpl extends AbstractDaoImpl<Author, AuthorPattern> implements AuthorDao {

    @Override
    public List<Author> find(ListParams<AuthorPattern> listParams) {
        String query = "SELECT * FROM  author ORDER BY average_rating, create_date";
        Query nativeQuery = (Query) entityManager.createNativeQuery(query, Author.class);
        nativeQuery = setParameters(listParams, nativeQuery);
        return nativeQuery.getResultList();
    }

    private Query setParameters(ListParams<AuthorPattern> listParams, Query nativeQuery) {
        AuthorPattern pattern = listParams != null ? listParams.getPattern() : null;
            if (listParams != null && listParams.getLimit() != null && listParams.getOffset() != null) {
                nativeQuery.setFirstResult(listParams.getOffset()).setMaxResults(listParams.getLimit());
            }

        return nativeQuery;
    }

    @Override
    public Integer totalRecords(ListParams<AuthorPattern> listParams) {
        String query = "SELECT Count(author.id) FROM author";
        Query nativeQuery = (Query) entityManager.createNativeQuery(query);
        return Integer.parseInt(nativeQuery.getSingleResult().toString());
    }

    @Override
    public List<Author> readTopFive() throws DaoException {
        return entityManager.createNativeQuery("SELECT * FROM author ORDER BY average_rating", Author.class).setFirstResult(0).setMaxResults(5).getResultList();
    }

    @Override
    public Author deleteAuthor(Integer idAuthor) throws DaoException {
        if (idAuthor == null) {
            throw new DaoException("AbstractEntity id can't be null");
        }
        Author author = get(idAuthor);
        if (author == null) {
            throw new DaoException("Such author doesn't exist");
        }
        Integer countBook = Integer.parseInt(
            entityManager.createNativeQuery("SELECT count(1) FROM author_book WHERE author_id = :authorId")
                .setParameter("authorId", author.getId()).getSingleResult().toString());
        if (countBook != 0) {
            return author;
        }
        try {
            super.delete(author.getId());
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return null;
    }

    @Override
    public List<Author> bulkDelete(List<Integer> idEntities) throws DaoException {
        List<Author> notRemove = new ArrayList<>();
        for (Integer entityId : idEntities) {
            Author author = deleteAuthor(entityId);
            if (author != null) {
                notRemove.add(author);
            }
        }
        return notRemove;
    }
}

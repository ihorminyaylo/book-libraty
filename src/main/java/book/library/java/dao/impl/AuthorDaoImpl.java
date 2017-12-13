package book.library.java.dao.impl;

import book.library.java.dao.AuthorDao;
import book.library.java.dto.ListParams;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.Book;
import book.library.java.model.pattern.AuthorPattern;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorDaoImpl extends AbstractDaoImpl<Author, AuthorPattern> implements AuthorDao {

    /*@Override
    public List<Author> find(ListParams listParams) {
        return entityManager.createNativeQuery("SELECT * FROM author ORDER BY average_rating, create_date", Author.class).setFirstResult(listParams.getOffset()).setMaxResults(listParams.getLimit()).getResultList();
    }*/

    @Override
    public List<Author> find(ListParams<AuthorPattern> listParams) {
        StringBuilder startQuery = new StringBuilder("SELECT * FROM  author");
        StringBuilder query = new StringBuilder(generateQueryWithParams(listParams, startQuery));
        if (listParams.getSortParams() != null && listParams.getSortParams().getParameter() != null && listParams.getSortParams().getStatus() != null && listParams.getSortParams().getStatus()) {
            query.append(" ORDER BY :parameter, average_rating, create_date");
        }
        else {
            query.append(" ORDER BY average_rating, create_date");
        }
        Query nativeQuery = (Query) entityManager.createNativeQuery(query.toString(), Author.class);
        nativeQuery = setParameters(listParams, nativeQuery, "find");
        if (listParams.getSortParams() != null && listParams.getSortParams().getParameter() != null && listParams.getSortParams().getStatus() != null && listParams.getSortParams().getStatus()) {
            nativeQuery.setParameter("parameter", listParams.getSortParams().getParameter());
        }
        List<Author> authorList = nativeQuery.getResultList();
        return authorList;
    }

    public StringBuilder generateQueryWithParams(ListParams<AuthorPattern> listParams, StringBuilder query) {
        AuthorPattern pattern = listParams != null ? listParams.getPattern() : null;
        return query;
    }
    public Query setParameters(ListParams<AuthorPattern> listParams, Query nativeQuery, String type) {
        AuthorPattern pattern = listParams != null ? listParams.getPattern() : null;
        if (type.equals("find")) {
            if (listParams != null && listParams.getLimit() != null && listParams.getOffset() != null) {
                System.out.println(listParams.getLimit() + "dasdasdas" + listParams.getOffset());
                System.out.println(listParams.getLimit() + listParams.getOffset());
                nativeQuery.setFirstResult(listParams.getOffset()).setMaxResults(listParams.getLimit());
            }
        }
        if (pattern != null) {
        }
        return nativeQuery;
    }

    @Override
    public Integer totalRecords(ListParams<AuthorPattern> listParams) {
        StringBuilder query = new StringBuilder("SELECT Count(author.id) FROM author");
        Query nativeQuery = (Query) entityManager.createNativeQuery(generateQueryWithParams(listParams, query).toString());
        nativeQuery = setParameters(listParams, nativeQuery, "totalRecords");
        BigInteger bigInteger = (BigInteger) nativeQuery.getSingleResult();
        return bigInteger.intValue();
    }

    @Override
    public Author deleteAuthor(Integer idAuthor) throws DaoException {
        if (idAuthor == null) {
            throw new DaoException("Entity id can't be null");
        }
        Author author = get(idAuthor);
        author.setBooks(entityManager.createNativeQuery("SELECT * FROM book JOIN author_book ON book.id = author_book.book_id WHERE author_id = :authorId", Book.class).setParameter("authorId", author.getId()).getResultList());
        if (!author.getBooks().isEmpty()) {
            author.getBooks().forEach(book -> book.getAuthors());
            return author;
        }
        try {
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
            if (delete(entityId) != null);
            notRemove.add(get(entityId));
        }
        return notRemove;
    }

    @Override
    public List<Author> readByBook(Integer idBook) throws DaoException {
        return entityManager.createNativeQuery("SELECT id, first_name, second_name, create_date, average_rating FROM author as a JOIN author_book as ab ON a.id = ab.author_id WHERE ab.book_id = :idBook", Author.class).setParameter("idBook", idBook).getResultList();
    }


    //todo: not work
    @Override
    public List<Author> getByAverageRating() {
        StringBuilder query = new StringBuilder("SELECT * FROM authors INNER JOIN author_book_keys ON authors.id = author_book_keys.author_id INNER JOIN reviews ON author_book_keys.book_id = reviews.book_id");
        return entityManager.createNativeQuery(query.toString(), Author.class).getResultList();
    }
}

package book.library.java.dao;

import book.library.java.dto.BookWithAuthors;
import book.library.java.exception.DaoException;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;

import java.util.List;

/**
 * Represent a Book DAO with generic Book(type of entity) and BookPattern(type of Pattern)
 * A Book DAO have next methods: create, readTop, bulkDelete
 */
public interface BookDao extends Dao<Book, BookPattern> {
    /**
     * This method for create new book in Data Base
     *
     * @param bookWithAuthors
     * @return id of created book
     * @throws DaoException
     */
    Integer create(BookWithAuthors bookWithAuthors) throws DaoException;

    /**
     * This method for get top books from Data Base.
     *
     * @return List of books
     */
    List<Book> readTopFive();

    /**
     * This method for create new book in Data Base
     *
     * @param bookWithAuthors
     * @return id of created book
     * @throws DaoException
     */
    Integer update(BookWithAuthors bookWithAuthors) throws DaoException;


    /**
     * This method for bulk delete books by id
     *
     * @param idBooks
     * @throws DaoException
     */
    void bulkDelete(List<Integer> idBooks) throws DaoException;
}
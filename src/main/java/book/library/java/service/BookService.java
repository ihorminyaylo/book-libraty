package book.library.java.service;

import book.library.java.dto.BookWithAuthors;
import book.library.java.exception.BusinessException;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;

import java.util.List;

/**
 * Represent a Book Service
 * which extends of AbstractService with generic Book(type of entity) and BookPattern(type of Pattern for definite entity)
 * A Book Service have such methods: create, readTop, bulkDelete
 */
public interface BookService extends AbstractService<Book, BookPattern> {

    /**
     * This method for create book, where we get like parameter BooksWithAuthors(Book and authors who wrote this book)
     *
     * @param bookWithAuthors
     * @return id of book which we created.
     * @throws BusinessException
     */
    Integer create(BookWithAuthors bookWithAuthors) throws BusinessException;

    /**
     * This method for read top books. Count of top we pass like argument in method.
     *
     * @param count of we want to get top books
     * @return List books DTO
     */
    List<Book> readTop(Integer count);

    /**
     * This method for delete one book by id
     *
     * @param idEntities is List of id books which we want remove
     * @throws BusinessException
     */
    void bulkDelete(List<Integer> idEntities) throws BusinessException;
}

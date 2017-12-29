package book.library.java.service;

import book.library.java.dto.BookDto;
import book.library.java.dto.BookWithAuthors;
import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.list.ListParams;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;

import java.util.List;

/**
 * Represent a Book BaseService
 * which extends of BaseService with generic Book(type of entity) and BookPattern(type of Pattern for definite entity)
 * A Book BaseService have such methods: create, readTop, bulkDelete
 */
public interface BookService extends BaseService<Book, BookPattern> {

    /**
     * This method for create book, where we get like parameter BooksWithAuthors(Book and authors who wrote this book)
     *
     * @param bookWithAuthors
     * @return id of book which we created.
     * @throws BusinessException
     */
    Integer create(BookWithAuthors bookWithAuthors) throws BusinessException;

    BookDto readByBookId(Integer idBook) throws BusinessException;

    /**
     * @param listParams with condition type
     * @return
     * @throws BusinessException
     */
    ListEntityPage readBooks(ListParams<BookPattern> listParams) throws BusinessException;

    /**
     * This method for read top books.
     *
     * @return List books DTO
     */
    List<BookDto> readTopFive();

    Integer updateBook(BookWithAuthors bookWithAuthors) throws BusinessException;


    /**
     * This method for delete one book by id
     *
     * @param idEntities is List of id books which we want remove
     * @throws BusinessException
     */
    void bulkDelete(List<Integer> idEntities) throws BusinessException;
}

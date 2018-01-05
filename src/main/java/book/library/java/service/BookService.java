package book.library.java.service;

import book.library.java.dto.BookDto;
import book.library.java.dto.ListEntityPage;
import book.library.java.dto.ReviewPageDto;
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
public interface BookService extends BaseService<Book, BookPattern, BookDto> {

    /**
     * This method for create book, where we get like parameter BooksWithAuthors(Book and authors who wrote this book)
     *
     * @param book
     * @return id of book which we created.
     * @throws BusinessException
     */
    Integer create(Book book) throws BusinessException;

    /**
     * This method for get all types rating(stars) and count book of this rating
     *
     * @return List of Page DTO where definite next fields: rating and count
     */
    List<ReviewPageDto> getCountOfEachRating();

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
}

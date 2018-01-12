package book.library.java.dao;

import book.library.java.dto.ReviewPageDto;
import book.library.java.model.Book;
import book.library.java.model.pattern.BookPattern;

import java.util.List;

/**
 * Represent a Book DAO with generic Book(type of entity) and BookPattern(type of Pattern)
 * A Book DAO have next methods: getCountOfEachRating
 */
public interface BookDao extends Dao<Book, BookPattern> {
    /**
     * This method for get ReviewPageDto with fields rating(stars) - unique and count of each rating
     *
     * @return List of ReviewPageDto
     */
    List<ReviewPageDto> getCountOfEachRating();

    Boolean checkISBN(String isbn);
}
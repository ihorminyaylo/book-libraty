package book.library.java.dao;

import book.library.java.model.Author;
import book.library.java.model.pattern.AuthorPattern;

/**
 * Represent a Author DAO with generic Author(type of entity) and AuthorPattern(type of Pattern)
 */
public interface AuthorDao extends Dao<Author, AuthorPattern> {
}

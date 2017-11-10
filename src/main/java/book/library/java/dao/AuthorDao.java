package book.library.java.dao;

import book.library.java.model.Author;

import java.util.List;

public interface AuthorDao extends InterfaceDao<Author> {
    List<Author> getByAverageRating();
}

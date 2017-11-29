package book.library.java.dao;

import book.library.java.model.Author;

import java.util.List;

public interface AuthorDao extends AbstractDao<Author> {

    //todo: not work
    List<Author> getByAverageRating();
}

package book.library.java.dao;

import book.library.java.model.Author;

import java.math.BigInteger;
import java.util.List;

public interface AuthorDao extends InterfaceDao<Author> {

    //todo: not work
    List<Author> getByAverageRating();
}

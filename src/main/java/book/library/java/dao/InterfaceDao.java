package book.library.java.dao;

import book.library.java.exception.DaoException;

import java.util.List;

public interface InterfaceDao<T> {

    //todo: Remove, because we will use find with pattern
    List<T> findAll();

    void create(T entity) throws DaoException;
    T get(Integer entityId);
    <P> List<T> find(P pattern);
    void update(T entity) throws DaoException;
    void delete(Integer entityId) throws DaoException;

    List<T> findWithPagination(Integer page, Integer totalView);
}

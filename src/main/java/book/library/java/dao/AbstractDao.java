package book.library.java.dao;

import book.library.java.exception.DaoException;
import book.library.java.list.ListParams;

import java.util.List;

/**
 * Represent a Abstract DAO with generic T(type of entity) and P(type of Pattern for definite entity)
 * A Abstract DAO have CRUD methods(create, read(find), update, delete) and findAll, get, totalRecords
 */
public interface AbstractDao<T, P> {
    /**
     * This method for create new entity in Data Base.
     * @param entity
     * @return id of created entity
     * @throws DaoException
     */
    Integer create(T entity) throws DaoException;

    //todo:
    T get(Integer entityId) throws DaoException;

    List<T> findAll();

    List<T> find(ListParams<P> listParams) throws DaoException;

    void update(T entity) throws DaoException;

    Integer delete(Integer idEntity) throws DaoException;

    Integer totalRecords(ListParams<P> listParams);
}

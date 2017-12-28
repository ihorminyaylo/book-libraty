package book.library.java.dao;

import book.library.java.exception.BusinessException;
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

    /**
     * This method for get one entity by id from Data Base
     * @param entityId
     * @return entity
     * @throws DaoException
     */
    T get(Integer entityId) throws DaoException;

    /**
     * This method for get all entities from Data Base
     * @return List entity
     */
    List<T> findAll();

    /**
     * This method for get entities with condition from user
     * @param listParams
     * @return List entity
     * @throws DaoException
     */
    List<T> find(ListParams<P> listParams) throws DaoException, BusinessException;

    /**
     * This method for update already existent entity in Data Base
     * @param entity
     * @throws DaoException
     */
    void update(T entity) throws DaoException;

    /**
     * This method for delete entity from Data Base by id
     * @param idEntity
     * @return id of entity which we deleted
     * @throws DaoException
     */
    Integer delete(Integer idEntity) throws DaoException;

    /**
     * This method for get count of entity with our condition
     * @param listParams
     * @return count of entity in Data Base
     */
    Integer totalRecords(ListParams<P> listParams);
}

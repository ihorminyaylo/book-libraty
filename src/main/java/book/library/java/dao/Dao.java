package book.library.java.dao;

import book.library.java.exception.DaoException;
import book.library.java.list.ListParams;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Represent a Abstract DAO with generic T(type of entity) and P(type of Pattern for definite entity)
 * A Abstract DAO have CRUD methods(create, read(find), update, delete) and get(Integer id), findAll,
 * findTopFive, totalRecords, bulkDelete(List<Integer> idEntities), generateQueryWithParams, setParameters
 */
public interface Dao<T, P> {
    /**
     * This method for create new entity in Data Base.
     *
     * @param entity which we want to create
     * @return id of created entity
     * @throws DaoException on error
     */
    Integer create(T entity) throws DaoException;

    /**
     * This method for get entities with condition from user
     *
     * @param listParams for search by parameters
     * @return List entities
     * @throws DaoException on error
     */
    List<T> find(ListParams<P> listParams) throws DaoException;

    /**
     * This method for get all entities from Data Base
     *
     * @return List entities
     */
    List<T> findAll();

    /**
     * This method for get one entity by id from Data Base
     *
     * @param entityId id of entity
     * @return entity
     * @throws DaoException on error
     */
    T get(Integer entityId) throws DaoException;


    /**
     * This method for get entities with the best average rating for entities
     *
     * @return List entities
     */
    List<T> findTopFive();

    /**
     * This method for update already existent entity in Data Base
     *
     * @param entity which we want to update
     * @throws DaoException on error
     */
    void update(T entity) throws DaoException;

    /**
     * This method for delete entity from Data Base by id
     *
     * @param idEntity which we want to delete
     * @return id of entity which we deleted
     * @throws DaoException on error
     */
    Integer delete(Integer idEntity) throws DaoException;

    /**
     * This method for bulk delete entities from Data Base by id
     *
     * @param idEntities which we want to delete
     * @return idEntities with for our condition
     * @throws DaoException on error
     */
    List<Integer> bulkDelete(List<Integer> idEntities) throws DaoException;

    /**
     * This method for get count of entity with our condition
     *
     * @param listParams params from user
     * @return count of entity in Data Base
     */
    Integer totalRecords(ListParams<P> listParams) throws DaoException;

    /**
     * This method for generate query with our parammeters
     *
     * @param listParams    params for search
     * @param query         pass your query with general block
     * @param typeQueryFind type query is find - true, else - false
     * @return StringBuilder query
     */
    StringBuilder generateQueryWithParams(ListParams<P> listParams, StringBuilder query, Boolean typeQueryFind) throws DaoException;

    /**
     * This method for set parameters in our query
     *
     * @param listParams    params for search
     * @param query         query pass your query with general block
     * @param typeQueryFind type query is find - true, else - false
     * @return Query
     */
    Query setParameters(ListParams<P> listParams, Query query, Boolean typeQueryFind);
}

package book.library.java.service;

import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.list.ListParams;

/**
 * Represent a Abstract Service with generic T(type of entity) and P(type of Pattern for definite entity)
 * A Abstract Service have CRUD methods(create, read, update, delete)
 */
public interface AbstractService<T, P> {
    /**
     * This method for create entity, which we get like parameter
     *
     * @param entity
     * @return id of entity which we created.
     * @throws BusinessException
     */
    Integer create(T entity) throws BusinessException;

    /**
     * This method for get entities with params and get total items these entities.
     *
     * @param listParams with condition type
     * @return ListEntityPage with condition type
     * @throws BusinessException
     */
    ListEntityPage<T> read(ListParams<P> listParams) throws BusinessException;

    /**
     * This method for update entity, which we get like parameter
     *
     * @param entity
     * @throws BusinessException
     */
    void update(T entity) throws BusinessException;

    /**
     * This delete for update entity, which we get like parameter
     *
     * @param idEntity of entity which we can remove
     * @return id of entity which we remove.
     * @throws BusinessException
     */
    Integer delete(Integer idEntity) throws BusinessException;

    /**
     * This method for validate entity
     * @param entity
     */
    void validateEntity(T entity) throws BusinessException;
}

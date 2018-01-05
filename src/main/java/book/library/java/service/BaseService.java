package book.library.java.service;

import book.library.java.dto.AuthorDto;
import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.list.ListParams;
import book.library.java.model.Author;

import java.util.List;

/**
 * Represent a Abstract BaseService with generic T(type of entity) and P(type of Pattern for definite entity)
 * A Abstract BaseService have CRUD methods(create, read, update, delete)
 */
public interface BaseService<T, P, U> {
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
     * This method for get all entities.
     *
     * @return List authors
     */
    List<T> readAll();

    /**
     * This method for get entity DTO by id
     * @param idAuthor
     * @return Entity DTO
     */
    U readById(Integer idAuthor) throws BusinessException;

    /**
     * This method for update entity, which we get like parameter
     *
     * @param entity
     * @throws BusinessException
     */
    void update(T entity) throws BusinessException;

    /**
     * This method delete entity by id
     *
     * @param idEntity of entity which we can remove
     * @return id of entity with our condition.
     * @throws BusinessException
     */
    Integer delete(Integer idEntity) throws BusinessException;

    /**
     * This method for bulk delete entities
     * @param idEntities
     * @return id of entities with our condition.
     */
    List<Integer> bulkDelete(List<Integer> idEntities) throws BusinessException;

    /**
     * This method for validate entity
     *
     * @param entity
     */
    void validateEntity(T entity) throws BusinessException;
}

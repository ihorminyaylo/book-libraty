package book.library.java.dao;

import book.library.java.dto.ListParams;
import book.library.java.exception.DaoException;

import java.util.List;

public interface AbstractDao<T, P> {
    void create(T entity) throws DaoException;

    T get(Integer entityId);

    List<T> findAll();

    List<T> find(ListParams<P> listParams);

    void update(T entity) throws DaoException;

    Integer delete(Integer idEntity) throws DaoException;

    void bulkDelete(List<Integer> idEntities) throws DaoException;

    Integer totalRecords(ListParams<P> listParams);
}

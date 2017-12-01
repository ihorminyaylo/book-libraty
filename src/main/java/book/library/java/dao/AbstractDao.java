package book.library.java.dao;

import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.DaoException;

import java.util.List;

public interface AbstractDao<T> {
    void create(T entity) throws DaoException;

    T get(Integer entityId);

    List<T> findAll();

    List<T> find(ReadParamsDto readParamsDto);

    void update(T entity) throws DaoException;

    List<T> delete(List<Integer> idEntities) throws DaoException;

    Integer totalRecords();
}

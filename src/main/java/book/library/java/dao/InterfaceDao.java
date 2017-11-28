package book.library.java.dao;

import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.DaoException;

import java.math.BigInteger;
import java.util.List;

public interface InterfaceDao<T> {
    void create(T entity) throws DaoException;
    T get(Integer entityId);
    List<T> findAll();
    List<T> find(ReadParamsDto readParamsDto);
    void update(T entity) throws DaoException;
    void delete(Integer entityId) throws DaoException;
    Integer totalRecords();
}

package book.library.java.service;

import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;

public interface AbstractService<T> {
    void create(T t) throws BusinessException;
    <P> EntitiesAndPageDto<T> read(ReadParamsDto<P> readParamsDto) throws BusinessException;
    void update(T t) throws DaoException;
    void delete(Integer id) throws DaoException;
}

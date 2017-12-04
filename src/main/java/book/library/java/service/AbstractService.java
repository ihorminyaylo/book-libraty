package book.library.java.service;

import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;

import java.util.List;

public interface AbstractService<T> {
    void create(T t) throws BusinessException;

    <P> EntitiesAndPageDto<T> read(ReadParamsDto<P> readParamsDto) throws BusinessException;

    void update(T t) throws BusinessException;

    void bulkDelete(List<Integer> idEntities) throws BusinessException;
}

package book.library.java.service;

import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.ListParams;

public interface AbstractService<T, P> {
    Integer create(T t) throws BusinessException, DaoException;

    EntitiesAndPageDto<T> read(ListParams<P> listParams) throws BusinessException, DaoException;

    void update(T t) throws BusinessException, DaoException;

    Integer delete(Integer idEntity) throws BusinessException;

}

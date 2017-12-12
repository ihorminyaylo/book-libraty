package book.library.java.service;

import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.dto.ListParams;
import book.library.java.exception.BusinessException;

public interface AbstractService<T, P> {
    void create(T t) throws BusinessException;

    EntitiesAndPageDto<T> read(ListParams<P> listParams) throws BusinessException;

    void update(T t) throws BusinessException;

    Integer delete(Integer idEntity) throws BusinessException;

/*
    void bulkDelete(List<Integer> idEntities) throws BusinessException;
*/
}

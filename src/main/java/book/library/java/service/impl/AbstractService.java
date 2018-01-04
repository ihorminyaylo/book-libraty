package book.library.java.service.impl;

import book.library.java.dao.Dao;
import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.list.ListParams;
import book.library.java.model.AbstractEntity;
import book.library.java.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public abstract class AbstractService<D extends Dao<T, P>, T extends AbstractEntity, P> implements BaseService<T, P> {

    private D dao;

    AbstractService(D dao) {
        this.dao = dao;
    }

    public D getDao() {
        return dao;
    }

    @Override
    public Integer create(T entity) throws BusinessException {
        validateEntity(entity);
        try {
            return dao.create(entity);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public ListEntityPage<T> read(ListParams<P> listParams) throws BusinessException {
        List<T> listEntity;
        Integer totalItems = dao.totalRecords(listParams);
        try {
            listEntity = dao.find(listParams);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return new ListEntityPage<>(listEntity, totalItems);
    }


    @Override
    public void update(T entity) throws BusinessException {
        try {
            dao.update(entity);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Integer delete(Integer idEntity) throws BusinessException {
        try {
            dao.delete(idEntity);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return idEntity;
    }

    @Override
    public abstract void validateEntity(T entity) throws BusinessException;
}

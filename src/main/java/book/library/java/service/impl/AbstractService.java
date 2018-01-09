package book.library.java.service.impl;

import book.library.java.dao.Dao;
import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.list.ListParams;
import book.library.java.model.AbstractEntity;
import book.library.java.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public abstract class AbstractService<D extends Dao<T, P>, T extends AbstractEntity, P, U> implements BaseService<T, P, U> {

    private static final Logger log = LoggerFactory.getLogger(AbstractService.class);
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
            log.error("in create() exception - [{}] ", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public ListEntityPage<T> read(ListParams<P> listParams) throws BusinessException {
        List<T> listEntity;
        Integer totalItems = null;
        try {
            totalItems = dao.totalRecords(listParams);
        } catch (DaoException e) {
            log.error("in read() exception - [{}] ", e);
            throw new BusinessException(e);
        }
        try {
            listEntity = dao.find(listParams);
        } catch (Exception e) {
            log.error("in read() exception - [{}] ", e);
            throw new BusinessException(e);
        }
        return new ListEntityPage<>(listEntity, totalItems);
    }

    @Override
    public List<T> readAll() {
        return getDao().findAll();
    }

    @Override
    public U readById(Integer idEntity) throws BusinessException {
        try {
            return (U) getDao().get(idEntity);
        } catch (DaoException e) {
            log.error("in readAll() exception - [{}] ", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public void update(T entity) throws BusinessException {
        try {
            dao.update(entity);
        } catch (Exception e) {
            log.error("in update() exception - [{}] ", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public Integer delete(Integer idEntity) throws BusinessException {
        try {
            return dao.delete(idEntity);
        } catch (Exception e) {
            log.error("in delete() exception - [{}] ", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<Integer> bulkDelete(List<Integer> idEntities) throws BusinessException {
        try {
            return getDao().bulkDelete(idEntities);
        } catch (Exception e) {
            log.error("in bulkDelete() exception - [{}] ", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public abstract void validateEntity(T entity) throws BusinessException;
}

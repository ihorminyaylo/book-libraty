package book.library.java.service.impl;

import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.ListParams;
import book.library.java.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class AbstractServiceImpl<T, P> implements AbstractService<T, P> {

    private AbstractDaoImpl<T, P> entityDaoType;

    public AbstractServiceImpl(AbstractDaoImpl<T, P> entityDaoType) {
        this.entityDaoType = entityDaoType;
    }

    public AbstractServiceImpl() { // todo: useless constructor - remove
    }

    @Override
    public Integer create(T t) throws BusinessException, DaoException { // todo: change name of parameter, why DaoException in signature?
        try {
            return entityDaoType.create(t);
        } catch (Exception e) {
            throw new BusinessException();  // todo: must add base exception into BusinessException
        }
    }

    @Override
    public EntitiesAndPageDto<T> read(ListParams listParams) throws BusinessException, DaoException { // todo: generic for ListParams!, why DaoException in signature?
        List<T> listEntity;
        Integer totalItems = entityDaoType.totalRecords(listParams);
	    // todo: very strange condition and behaviour ???
        if (listParams.getLimit() == null || listParams.getOffset() != null) {
            listEntity = entityDaoType.find(listParams);
        } else {
            listEntity = entityDaoType.findAll();
        }
        return new EntitiesAndPageDto<>(listEntity, totalItems);
    }


    @Override
    public void update(T t) throws BusinessException, DaoException { // todo: change name of parameter, why DaoException in signature?
        try {
            entityDaoType.update(t);
        } catch (Exception e) {
            throw new BusinessException();// todo: must add base exception into BusinessException
        }
    }

    @Override
    public Integer delete(Integer idEntity) throws BusinessException {
        try {
            entityDaoType.delete(idEntity);
        } catch (Exception e) {
            throw new BusinessException();// todo: must add base exception into BusinessException
        }
        return idEntity;
    }
}

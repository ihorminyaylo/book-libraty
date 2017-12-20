package book.library.java.service.impl;

import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dto.ListEntityPage;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.list.ListParams;
import book.library.java.model.AbstractEntity;
import book.library.java.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class AbstractServiceImpl<T extends AbstractEntity, P> implements AbstractService<T, P> {

    private AbstractDaoImpl<T, P> entityDaoType;

    public AbstractServiceImpl(AbstractDaoImpl<T, P> entityDaoType) {
        this.entityDaoType = entityDaoType;
    }

    public AbstractServiceImpl() { // todo: useless constructor - remove
    }

    @Override
    public Integer create(T entity) throws BusinessException {
        try {
            return entityDaoType.create(entity);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public ListEntityPage<T> read(ListParams<P> listParams) throws BusinessException {
        List<T> listEntity = new ArrayList<>();
        Integer totalItems = entityDaoType.totalRecords(listParams);
        if (listParams.getLimit() != null || listParams.getOffset() != null) {
            try {
                listEntity = entityDaoType.find(listParams);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage(), e.getCause());
            }
        }
        return new ListEntityPage<>(listEntity, totalItems);
    }


    @Override
    public void update(T entity) throws BusinessException {
        try {
            entityDaoType.update(entity);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Integer delete(Integer idEntity) throws BusinessException {
        try {
            entityDaoType.delete(idEntity);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
        return idEntity;
    }
}

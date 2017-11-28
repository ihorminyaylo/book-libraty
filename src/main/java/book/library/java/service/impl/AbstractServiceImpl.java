package book.library.java.service.impl;

import book.library.java.dao.AbstractDao;
import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.service.AbstractService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@Transactional
public class AbstractServiceImpl<T> implements AbstractService<T> {

    private AbstractDao<T> entityDaoType;

    public  AbstractServiceImpl(AbstractDao<T> entityDaoType) {
        this.entityDaoType = entityDaoType;
    }

    public AbstractServiceImpl() {}

    @Override
    public void create(T t) throws BusinessException {

    }

    @Transactional
    @Override
    public <P> EntitiesAndPageDto<T> read(ReadParamsDto<P> readParamsDto) throws BusinessException {
        List<T> listEntity;
        Integer totalItems = entityDaoType.totalRecords();
        if (readParamsDto.getLimit() == null || readParamsDto.getOffset() != null) {
            listEntity = entityDaoType.find(readParamsDto);
        }
        else {
            listEntity = entityDaoType.findAll();
        }
        for (T entity: listEntity) {
            Hibernate.initialize(entity);
        }
        return new EntitiesAndPageDto<T>(listEntity, totalItems) ;
    }


    @Override
    public void update(T t) throws DaoException {

    }

    @Override
    public void delete(Integer id) throws DaoException {

    }
}

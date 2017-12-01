package book.library.java.service.impl;

import book.library.java.dao.impl.AbstractDaoImpl;
import book.library.java.dto.EntitiesAndPageDto;
import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.BusinessException;
import book.library.java.model.Author;
import book.library.java.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class AbstractServiceImpl<T> implements AbstractService<T> {

    private AbstractDaoImpl<T> entityDaoType;

    public AbstractServiceImpl(AbstractDaoImpl<T> entityDaoType) {
        this.entityDaoType = entityDaoType;
    }

    public AbstractServiceImpl() {
    }

    @Override
    public void create(T t) throws BusinessException {
        try {
            entityDaoType.create(t);
        } catch (Exception e) {
            throw new BusinessException();
        }
    }

    @Override
    public <P> EntitiesAndPageDto<T> read(ReadParamsDto<P> readParamsDto) throws BusinessException {
        List<T> listEntity;
        Integer totalItems = entityDaoType.totalRecords();
        if (readParamsDto.getLimit() == null || readParamsDto.getOffset() != null) {
            listEntity = entityDaoType.find(readParamsDto);
        } else {
            listEntity = entityDaoType.findAll();
        }
        return new EntitiesAndPageDto<>(listEntity, totalItems);
    }


    @Override
    public void update(T t) throws BusinessException {
        try {
            entityDaoType.update(t);
        } catch (Exception e) {
            throw new BusinessException();
        }
    }

    @Override
    public List<T> delete(List<Integer> idEntities) throws BusinessException {
        try {
            return entityDaoType.delete(idEntities);
        } catch (Exception e) {
            throw new BusinessException();
        }
    }
}

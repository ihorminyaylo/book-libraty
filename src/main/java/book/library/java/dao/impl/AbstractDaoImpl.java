package book.library.java.dao.impl;

import book.library.java.dao.AbstractDao;
import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractDaoImpl<T> implements AbstractDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> entityType;

    public AbstractDaoImpl() {
        this.entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void create(T entity) throws DaoException {
        if (entity == null) {
            throw new DaoException("Entity can't be null");
        }
        entityManager.persist(entity);
    }

    @Override
    public T get(Integer id) {
        return entityManager.find(entityType, id);
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("FROM " + entityType.getName()).getResultList();
    }

    @Override
    public List<T> find(ReadParamsDto readParamsDto) {
        return entityManager.createQuery("FROM " + entityType.getName()).setFirstResult(readParamsDto.getOffset()).setMaxResults(readParamsDto.getLimit()).getResultList();
    }

    @Override
    public void update(T entity) throws DaoException {
        if (entity == null) {
            throw new DaoException("Entity can't be null");
        }
        entityManager.merge(entity);
    }

    @Override
    public void delete(Integer idEntity) throws DaoException {
        if (idEntity == null) {
            throw new DaoException("Entity id can't be null");
        }
        T entity = get(idEntity);
        try {
            entityManager.remove(entity);
        } catch (Exception e) {
            throw new DaoException();
        }
    }

    @Override
    public void bulkDelete(List<Integer> idEntities) throws DaoException {
        for (Integer entityId : idEntities) {
            if (entityId == null) {
                throw new DaoException("Entity id can't be null");
            }
            T entity = get(entityId);
            try {
                entityManager.remove(entity);
            } catch (Exception e) {
                throw new DaoException();
            }
        }
    }

    @Override
    public Integer totalRecords() {
        String queryString = "SELECT Count(*) FROM " + entityType.getName();
        Query query = entityManager.createQuery(queryString);
        return (int) (long) query.getSingleResult();
    }
}

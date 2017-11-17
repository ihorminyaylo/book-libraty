package book.library.java.dao;

import book.library.java.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class AbstractDao<T> implements InterfaceDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> entityType;

    @SuppressWarnings("unchecked")
    public  AbstractDao() {
        this.entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T get(Integer id) {
        return entityManager.find(entityType, id);
    }

    @Override
    public <P> List<T> find(P pattern) {

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return entityManager.createQuery("FROM " + entityType.getName()).getResultList();
    }

    @Override
    public void create(T entity) throws DaoException {
        if (entity == null) {
            throw new DaoException("Entity can't be null");
        }
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) throws DaoException {
        if (entity == null) {
            throw new DaoException("Entity can't be null");
        }
        entityManager.merge(entity);
    }

    @Override
    public void delete(Integer entityId) throws DaoException {
        if (entityId == null) {
            throw new DaoException("Entity id can't be null");
        }
        T entity = get(entityId);
        entityManager.remove(entity);
    }

    @Override
    public List<T> findWithPagination(Integer page, Integer totalView) {
        return entityManager.createQuery("FROM " + entityType.getName()).setFirstResult((page-1) * totalView).setMaxResults(totalView).getResultList();
    }
}

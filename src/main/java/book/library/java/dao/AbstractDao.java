package book.library.java.dao;

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
    public T get(String id) {
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
    public void create(T entity) { entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(String entityId) {
        T entity = get(entityId);
        entityManager.remove(entity);
    }
}

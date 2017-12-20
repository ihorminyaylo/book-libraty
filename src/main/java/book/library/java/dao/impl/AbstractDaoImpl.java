package book.library.java.dao.impl;

import book.library.java.dao.AbstractDao;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.model.Book;
import book.library.java.model.ListParams;
import book.library.java.model.Review;
import book.library.java.model.TypeSort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractDaoImpl<T, P> implements AbstractDao<T, P> {

    private final Class<T> entityType;
    @PersistenceContext
    EntityManager entityManager;

    AbstractDaoImpl() {
        entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Integer create(T entity) throws DaoException {
        if (entity == null) {
            throw new DaoException("Entity can't be null");
        }
        // todo: what id entity.getId() != null ?
        entityManager.persist(entity);
        // todo: all next line must be removed! Please review model classes!
        if (entity instanceof Author) {
            return ((Author) entity).getId();
        }
        if (entity instanceof Book) {
            return ((Book) entity).getId();
        }
        if (entity instanceof Review) {
            return ((Review) entity).getId();
        }
        return null;
    }

    @Override
    public T get(Integer id) {
        return entityManager.find(entityType, id); // todo: what if id == null ?
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("FROM " + entityType.getName()).getResultList();
    }

    @Override
    public List<T> find(ListParams<P> listParams) throws DaoException { // todo: not part of this abstraction - remove this method or make it as abstract
        return entityManager.createQuery("FROM " + entityType.getName()).setFirstResult(listParams.getOffset()).setMaxResults(listParams.getLimit()).getResultList();
    }

    @Override
    public void update(T entity) throws DaoException {
        if (entity == null) {
            throw new DaoException("Entity can't be null");
        }
	    // todo: what if id == null ?
        entityManager.merge(entity);
    }

    @Override
    public Integer delete(Integer idEntity) throws DaoException {
        if (idEntity == null) {
            throw new DaoException("Entity id can't be null");
        }
        T entity = get(idEntity);
        try {
	        // todo: what if entity == null ?
            entityManager.remove(entity);
        } catch (Exception e) {
            throw new DaoException();  // todo: must add base exception into DaoException
        }
        return idEntity;
    }

    @Override
    public void bulkDelete(List<Integer> idEntities) throws DaoException { // todo: not part of this abstraction - remove this method
        for (Integer entityId : idEntities) {
            delete(entityId);
        }
    }

    @Override
    public Integer totalRecords(ListParams<P> listParams) {
        String queryString = "SELECT Count(*) FROM " + entityType.getName();
        Query query = entityManager.createQuery(queryString);
        return (int) (long) query.getSingleResult(); // todo: wrong implementation! Rework!
    }

    void generateQueryWithSortParams(ListParams<P> listParams, StringBuilder query) throws DaoException {
        if (!listParams.getSortParams().getParameter().contains(" ")) { // todo: wrong validation! Rework!
            query.append(" ORDER BY ").append(listParams.getSortParams().getParameter());
            if ("down".equals(listParams.getSortParams().getType())) { // todo: wrong implementation! Rework!
                query.append(' ').append(TypeSort.DESC);
            } else {
                query.append(' ').append(TypeSort.ASC);
            }
        } else {
            throw new DaoException();
        }
    }
}

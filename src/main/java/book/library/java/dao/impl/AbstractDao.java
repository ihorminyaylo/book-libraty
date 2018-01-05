package book.library.java.dao.impl;

import book.library.java.dao.Dao;
import book.library.java.exception.DaoException;
import book.library.java.list.ListParams;
import book.library.java.list.SortParams;
import book.library.java.model.AbstractEntity;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public abstract class AbstractDao<T extends AbstractEntity, P> implements Dao<T, P> {

    private final Class<T> entityType;
    @PersistenceContext
    EntityManager entityManager;

    AbstractDao() {
        entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Integer create(T entity) throws DaoException {
        if (entity == null) {
            throw new DaoException("AbstractEntity can't be null");
        }
        if (entity.getId() != null) {
            throw new DaoException("Can't set id. Id generated in Data Base");
        }
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    public List<T> find(ListParams<P> listParams) throws DaoException {
        StringBuilder queryString = new StringBuilder("SELECT * FROM " + entityType.getSimpleName());
        generateQueryWithParams(listParams, queryString, true);
        generateQueryWithSortParams(listParams, queryString);
        Query query = (Query) entityManager.createNativeQuery(queryString.toString(), entityType);
        query = setParameters(listParams, query, true);
        return query.getResultList();
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("FROM " + entityType.getName()).getResultList();
    }

    @Override
    public T get(Integer id) throws DaoException {
        if (id == null) {
            throw new DaoException("Id of entity can't be null");
        }
        return entityManager.find(entityType, id);
    }

    @Override
    public List<T> findTopFive() {
        StringBuilder query = new StringBuilder("SELECT * FROM " + entityType.getSimpleName() + " ORDER BY average_rating");
        return entityManager.createNativeQuery(query.toString(), entityType).setMaxResults(5).getResultList();
    }


    @Override
    public void update(T entity) throws DaoException {
        if (entity == null || entity.getId() == null) {
            throw new DaoException("AbstractEntity can't be null");
        }
        entityManager.merge(entity);
    }

    @Override
    public Integer delete(Integer idEntity) throws DaoException {
        if (idEntity == null) {
            throw new DaoException("AbstractEntity id can't be null");
        }
        T entity = get(idEntity);
        if (entity == null) {
            throw new DaoException("Entity with this id does not exist");
        }
        try {
            entityManager.remove(entity);
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return idEntity;
    }

    @Override
    public List<Integer> bulkDelete(List<Integer> idEntities) throws DaoException {
        List<Integer> listId = new ArrayList<>();
        for (Integer idBook : idEntities) {
            listId.add(delete(idBook));
        }
        return listId;
    }

    @Override
    public Integer totalRecords(ListParams<P> listParams) {
        StringBuilder queryString = new StringBuilder("SELECT Count(*) FROM " + entityType.getSimpleName());
        Query query = (Query) entityManager.createNativeQuery(generateQueryWithParams(listParams, queryString, false).toString());
        query = setParameters(listParams, query, false);
        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public StringBuilder generateQueryWithParams(ListParams<P> listParams, StringBuilder query, Boolean typeQueryFind) {
        if (typeQueryFind) {
            if (listParams.getPattern() != null) {
                addSortParams(listParams, query);
            }
        }
        return query;
    }

    @Override
    public Query setParameters(ListParams<P> listParams, Query query, Boolean typeQueryFind) {
        if (typeQueryFind) {
            if (listParams.getLimit() != null && listParams.getOffset() != null) {
                query.setFirstResult(listParams.getOffset()).setMaxResults(listParams.getLimit());
            }
        }
        return query;
    }

    void generateQueryWithSortParams(ListParams<P> listParams, StringBuilder query) throws DaoException {
        SortParams sortParams = listParams.getSortParams();
        if (sortParams != null && sortParams.getParameter() != null && sortParams.getType() != null) {
            for (Field field : entityType.getFields()) {
                if (sortParams.getParameter().equalsIgnoreCase(field.getName())) {
                    query.append(" ORDER BY ").append(sortParams.getParameter()).append(' ').append(sortParams.getType());
                    return;
                }
            }
        } else {
            query.append(" ORDER BY create_date");
        }
    }

    void addSortParams(ListParams<P> listParams, StringBuilder query) {
        SortParams sortParams = listParams.getSortParams();
        if (sortParams != null && sortParams.getParameter() != null && sortParams.getType() != null) {
            Field[] fields = entityType.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
            }
            // : todo
            String checkFieldName = sortParams.getParameter();
            if (checkFieldName.contains("_")) {
                checkFieldName = checkFieldName.replace("_", "");
            }
            for (Field field : fields) {
                if (checkFieldName.equalsIgnoreCase(field.getName())) {
                    query.append(" ORDER BY ").append(sortParams.getParameter()).append(' ').append(sortParams.getType());
                }
            }
        }
    }
}

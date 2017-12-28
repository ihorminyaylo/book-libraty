package book.library.java.dao.impl;

import book.library.java.dao.AbstractDao;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.list.SortParams;
import book.library.java.model.AbstractEntity;
import book.library.java.model.Author;
import book.library.java.model.Book;
import book.library.java.list.ListParams;
import book.library.java.model.Review;
import book.library.java.list.TypeSort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("unchecked")
@Service
public abstract class AbstractDaoImpl<T extends AbstractEntity, P> implements AbstractDao<T, P> {

    private final Class<T> entityType;
    @PersistenceContext
    EntityManager entityManager;

    AbstractDaoImpl() {
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
    public T get(Integer id) throws DaoException {
        if(id == null) {
            throw new DaoException("Id of entity can't be null");
        }
        return entityManager.find(entityType, id);
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("FROM " + entityType.getName()).getResultList();
    }

    @Override
    public abstract List<T> find(ListParams<P> listParams) throws DaoException, BusinessException;

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
    public Integer totalRecords(ListParams<P> listParams) {
        String queryString = "SELECT Count(*) FROM " + entityType.getName();
        Query query = entityManager.createQuery(queryString);
        return Integer.parseInt(query.getSingleResult().toString());
    }

    void generateQueryWithSortParams(ListParams<P> listParams, StringBuilder query) throws DaoException {
        SortParams sortParams = listParams.getSortParams();
        if (sortParams != null && sortParams.getParameter() != null && sortParams.getType() != null) {
            for (Field field :entityType.getFields()) {
                if (sortParams.getParameter().equals(field.getName())) {
                    query.append(" ORDER BY ").append(sortParams.getParameter()).append(' ').append(sortParams.getType());
                    return;
                }
            }
            throw new DaoException("The field isn't correct");
        }
        else {
            query.append(" ORDER BY create_date");
        }
    }
}

package book.library.java.dao;

import java.util.List;

public interface InterfaceDao<T> {

    //todo: Remove, because we will use find with pattern
    List<T> findAll();

    void create(T entity);
    T get(String entityId);
    <P> List<T> find(P pattern);
    void update(T entity);
    void delete(String entityId);

}

package book.library.java.dao;

import java.util.List;

public interface InterfaceDao<T> {

    T get(String id);
    List<T> getAll();
    void add(T entity);
    void set(T entity);
    void delete(String entityId);

}

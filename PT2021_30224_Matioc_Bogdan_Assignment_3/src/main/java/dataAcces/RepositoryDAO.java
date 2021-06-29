package dataAcces;

import java.util.List;

/**
 *this class contains the header of the methods for interacting with the database
 */
public interface RepositoryDAO<T> {
    List<T> findAll();
    T findById(int id);
    List<T> findByField(String fieldName, String fieldValue);
    void add(T object);
    void update(T object);
    void delete(int id);
}

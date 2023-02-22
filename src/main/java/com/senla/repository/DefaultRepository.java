package com.senla.repository;

import java.util.List;

public interface DefaultRepository<T, ID> {

    void save(T t);
    T findById(ID id);
    List<T> findAll();
    void update (T t);
    void delete (T t);
}

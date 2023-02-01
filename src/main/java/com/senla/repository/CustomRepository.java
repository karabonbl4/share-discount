package com.senla.repository;

public interface CustomRepository<T> {

    T findById(Long id);
    T saveOrUpdate(T t);
    void delete(T t);
}

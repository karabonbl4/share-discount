package com.senla.repository;

import com.senla.model.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DefaultRepository<T extends Entity> {

    protected Map<Long, T> alternateDB;

    protected DefaultRepository() {
        this.alternateDB = new HashMap<>();
    }

    public T findById(Long id) {
        return alternateDB.get(id);
    }

    public List<T> findAll(){
        return new ArrayList<>(alternateDB.values());
    }

    public T saveOrUpdate(T t) {
        alternateDB.put(t.getId(), t);
        return alternateDB.get(t.getId());
    }

    public void delete(T t) {
        alternateDB.remove(t.getId());
    }
}

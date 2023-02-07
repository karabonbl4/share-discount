package com.senla.repository;

import com.senla.model.Trademark;

import java.util.List;

public interface TrademarkRepository {
    void save(Trademark t);

    Trademark findById(Long id);

    List<Trademark> findAll();

    void update(Trademark t);

    void delete(Trademark t);
}

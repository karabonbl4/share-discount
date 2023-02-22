package com.senla.repository;

import com.senla.model.Purchase;

import java.util.List;

public interface PurchaseRepository {
    void save(Purchase t);

    Purchase findById(Long id);

    List<Purchase> findAll();

    void update(Purchase t);

    void delete(Purchase t);

    Purchase findByUserId(Long id);
}

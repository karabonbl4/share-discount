package com.senla.repository;


import com.senla.model.DiscountPolicy;

import java.util.List;

public interface DiscountPolicyRepository {
    void save(DiscountPolicy t);

    DiscountPolicy findById(Long id);

    List<DiscountPolicy> findAll();

    void update(DiscountPolicy t);

    void delete(DiscountPolicy t);
}

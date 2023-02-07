package com.senla.repository;



import com.senla.model.DiscountCard;

import java.util.List;

public interface DiscountCardRepository {
    void save(DiscountCard t);
    DiscountCard findById(Long id);
    List<DiscountCard> findAll();
    void update (DiscountCard t);
    void delete (DiscountCard t);
}

package com.senla.dao;

import com.senla.model.entity.DiscountCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Long> {
    List<DiscountCard> findByOwner_Id(Long userId);
}

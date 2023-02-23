package com.senla.dao;

import com.senla.model.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCard_Id(Long cardId);
    List<Purchase> findByUser_Id(Long userId);
}

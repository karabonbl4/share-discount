package com.senla.dao;

import com.senla.model.entity.Purchase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByCard_Id(Long cardId, Pageable paging);

    List<Purchase> findByBuyer_Id(Long userId, Pageable paging);

    List<Purchase> findByCard_DiscountPolicy_Trademark_Id(Long id, Pageable paging);

    Purchase findByName(String name);
}

package com.senla.dao;

import com.senla.model.entity.DiscountPolicy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Long> {

    List<DiscountPolicy> findByTrademark_Id(Long id, Pageable paging);

    DiscountPolicy findByDiscountCards_Id(Long cardId);
}

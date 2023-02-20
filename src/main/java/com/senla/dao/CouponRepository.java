package com.senla.dao;

import com.senla.model.entity.Coupon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @EntityGraph(value = "fetch.purchase", type = EntityGraph.EntityGraphType.LOAD)
    Coupon getCouponByPurchases_Id(Long id);
}

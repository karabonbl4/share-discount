package com.senla.dao;

import com.senla.model.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Coupon getCouponByPurchases_Id(Long id);
}

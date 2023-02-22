package com.senla.repository;

import com.senla.model.Coupon;
import com.senla.model.Purchase;

import java.util.List;

public interface CouponRepository {
    void save(Coupon t);
    Coupon findById(Long id);
    List<Coupon> findAll();
    void update (Coupon t);
    void delete (Coupon t);


    Coupon getCouponByPurchase(Purchase purchase);

}

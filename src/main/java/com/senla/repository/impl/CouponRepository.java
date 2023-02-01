package com.senla.repository.impl;

import com.senla.repository.DefaultRepository;
import com.senla.model.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepository extends DefaultRepository<Coupon> {

}

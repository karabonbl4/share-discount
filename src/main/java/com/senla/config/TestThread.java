package com.senla.config;

import com.senla.controller.CouponController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestThread implements Runnable{
    private final CouponController couponController;

    @Override
    public void run() {

        System.out.println(couponController.findById("1"));
        System.out.println(couponController.update("{\"id\":1,\"name\":\"coupon144\",\"startDate\":\"2011-11-10\",\"endDate\":\"2011-11-13\",\"discount\":0.53,\"used\":false}"));
    }
}

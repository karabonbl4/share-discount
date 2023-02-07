package com.senla;

import com.senla.controller.CouponController;
import com.senla.controller.PurchaseController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.senla")
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        CouponController couponController = applicationContext.getBean(CouponController.class);
        PurchaseController purchaseController = applicationContext.getBean(PurchaseController.class);
        String purchaseById = purchaseController.findById("1");
        System.out.println(couponController.getCouponByPurchase(purchaseById));


    }
}

package com.senla;

import com.senla.controller.CouponController;
import com.senla.controller.DiscountCardController;
import com.senla.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.senla")
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        String separator = "\n------------------------------------------------------\n";
        //CouponController
        CouponController couponController = applicationContext.getBean(CouponController.class);
        String jsonCoupon1 = "{\"id\":1,\"name\":\"coupon1\",\"used\":false}";
        String jsonCoupon2 = "{\"id\":2,\"name\":\"coupon2\",\"used\":true}";
        System.out.println(separator.concat(couponController.create(jsonCoupon1)) );
        System.out.println(couponController.create(jsonCoupon2));

        System.out.println(couponController.findById("1"));
        System.out.println(String.format("Coupon %s is deleted: %s", jsonCoupon2, couponController.delete(jsonCoupon2)));
        jsonCoupon1 = "{\"id\":1,\"name\":\"coupon1update\",\"used\":true}";
        System.out.println(couponController.update(jsonCoupon1).concat(separator));

        //DiscountCardController
        DiscountCardController discountCardController = applicationContext.getBean(DiscountCardController.class);
        String jsonCard1 = "{\"id\":1,\"name\":\"card1\",\"number\":123123123, \"discount\":12.15, \"\"}";
        String jsonCard2 = "{\"id\":2,\"name\":\"card2\",\"used\":true}";
        System.out.println(separator.concat(couponController.create(jsonCoupon1)) );
        System.out.println(couponController.create(jsonCoupon2));

        System.out.println(couponController.findById("1"));
        System.out.println(String.format("Coupon %s is deleted: %s", jsonCoupon2, couponController.delete(jsonCoupon2)));
        jsonCoupon1 = "{\"id\":1,\"name\":\"coupon1update\",\"used\":true}";
        System.out.println(couponController.update(jsonCoupon1).concat(separator));
    }
}

package com.senla;

import com.senla.config.TestThread;
import com.senla.controller.CouponController;
import com.senla.controller.TrademarkController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.senla")
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        CouponController couponController = applicationContext.getBean(CouponController.class);
        System.out.println(couponController.findById("1"));
//        TrademarkController trademarkController = applicationContext.getBean(TrademarkController.class);
//        System.out.println(trademarkController.findById("1"));
//        Thread testThread = new Thread(new TestThread(applicationContext.getBean(CouponController.class)), "testThread") ;
//        testThread.start();
//        trademarkController.update("{\"id\":2,\"title\":\"trademark6\"}");
    }
}

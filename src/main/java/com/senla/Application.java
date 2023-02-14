package com.senla;

import com.senla.controller.CouponController;
<<<<<<< HEAD
import com.senla.controller.PurchaseController;
=======
import com.senla.controller.UserController;
>>>>>>> main
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.senla")
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        CouponController couponController = applicationContext.getBean(CouponController.class);
<<<<<<< HEAD
        PurchaseController purchaseController = applicationContext.getBean(PurchaseController.class);
        String purchaseById = purchaseController.findById("1");
        System.out.println(couponController.getCouponByPurchase(purchaseById));
=======
        UserController userController = applicationContext.getBean(UserController.class);
        String byId = userController.findById("1");
        System.out.println(byId);
        String updateUser = "{\"id\":1,\"firstName\":\"Ivanko\",\"surName\":\"Ivanchich\",\"phoneNumber\":\"+333333333\",\"email\":\"ivanko@ivanchich.by\",\"birthday\":\"1999-10-10\",\"score\":0.00,\"isActive\":true}";
        String createUser = "{\"id\":2,\"firstName\":\"Petya\",\"surName\":\"Petrov\",\"phoneNumber\":\"+444444444\",\"email\":\"petya@petrov.by\",\"birthday\":\"1995-10-15\",\"score\":0.05,\"isActive\":true}";

        System.out.println(userController.update(updateUser));
        System.out.println(userController.save(createUser));
        userController.delete(createUser);
        System.out.println(couponController.findById("1"));
        Thread testThread = new Thread(new TestThread(applicationContext.getBean(CouponController.class)), "testThread") ;
        testThread.start();
>>>>>>> main
    }
}

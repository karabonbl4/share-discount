package com.senla.repository.impl;


import com.senla.model.Coupon;
import com.senla.model.Coupon_;
import com.senla.model.Purchase;
import com.senla.model.Purchase_;
import com.senla.repository.CouponRepository;
import com.senla.repository.DefaultRepositoryImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;


@Repository
public class CouponRepositoryImpl extends DefaultRepositoryImpl<Coupon, Long> implements CouponRepository {

    @SuppressWarnings("unchecked")
    public Coupon getCouponByPurchase(Purchase purchase){
        EntityManager entityManager= super.entityManagerFactoryBean.getNativeEntityManagerFactory().createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder.createQuery(Coupon.class);
        Root<Coupon> couponRoot = criteriaQuery.from(Coupon.class);
        Join<Object, Object> purch = (Join<Object, Object>) couponRoot.fetch(Coupon_.PURCHASES);

        ParameterExpression<String> pNamePurch = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.like(purch.get(Purchase_.NAME), pNamePurch));

        TypedQuery<Coupon> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(pNamePurch, purchase.getName());
        return query.getSingleResult();
    }

}

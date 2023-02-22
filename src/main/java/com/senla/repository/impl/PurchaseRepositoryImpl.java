package com.senla.repository.impl;

import com.senla.model.Purchase;
import com.senla.repository.DefaultRepositoryImpl;
import com.senla.repository.PurchaseRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class PurchaseRepositoryImpl extends DefaultRepositoryImpl<Purchase, Long> implements PurchaseRepository {


    @Override
    public Purchase findByUserId(Long id) {
        EntityManager entityManager = super.entityManagerFactoryBean.getNativeEntityManagerFactory().createEntityManager();
        Query from_purchase = entityManager.createQuery("from Purchase p where p.userId.id = :id");
        from_purchase.setParameter("id", id);
        return (Purchase) from_purchase.getSingleResult();
    }
}

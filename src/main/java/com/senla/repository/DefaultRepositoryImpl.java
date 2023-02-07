package com.senla.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class DefaultRepositoryImpl<T, ID> implements DefaultRepository<T, ID>{

    private final Class<T> entityClass;
    private final Class<ID> idClazz;
    @Autowired
    protected LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;

    @SuppressWarnings("unchecked")
    public DefaultRepositoryImpl() {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.idClazz = (Class<ID>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void save(T entity) {
        EntityManager entityManager = entityManagerFactoryBean.getNativeEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
    }
    @Override
    public T findById(ID id) {
        EntityManager entityManager = entityManagerFactoryBean.getNativeEntityManagerFactory().createEntityManager();

        T t = entityManager.find(this.entityClass, id);

        return t;
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        EntityManager entityManager = entityManagerFactoryBean.getNativeEntityManagerFactory().createEntityManager();
        return entityManager.createQuery("from" + entityClass.getName()).getResultList();
    }
    @Override
    public void update(T entity) {
        EntityManager entityManager = entityManagerFactoryBean.getNativeEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(entity);
        transaction.commit();
    }
    @Override
    public void delete(T entity) {
        EntityManager entityManager = entityManagerFactoryBean.getNativeEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entity);
        transaction.commit();
    }
}

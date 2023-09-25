package br.ucs.classleague.infrastructure.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transaction;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class GenericDAO<T, ID extends Serializable> {
    private final Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public Optional<T> findById(ID id) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(entityClass, id));
        } finally {
            entityManager.close();
        }
    }
    
    public List<T> findAll() {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
        } finally {
            entityManager.close();
        }
    }

//    public void save(T entity) {
//        EntityManager entityManager = EntityManagerProvider.getEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//
//        try {
//            transaction.begin();
//            if (entityManager.find(T, entity) != null) {
//                entityManager.merge(entity);
//            } else {
//                entityManager.persist(entity);
//            }
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null && transaction.isActive()) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    public void deleteById(ID id) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//
//        try {
//            transaction.begin();
//            T entity = entityManager.find(entityClass, id);
//            if (entity != null) {
//                entityManager.remove(entity);
//            }
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null && transaction.isActive()) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            entityManager.close();
//        }
//    }
}
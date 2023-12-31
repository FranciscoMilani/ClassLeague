package br.ucs.classleague.infrastructure.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }
    
    public List<T> findAll() {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    public T create(T entity) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        
        return null;
    }

    public void deleteById(ID id) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            T entity = entityManager.find(entityClass, id);
            if (entity != null) {
                entityManager.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public T update(T entity) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        T updatedEntity = null;
        
        try {
            entityManager.getTransaction().begin();
            updatedEntity = entityManager.merge(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        
        return updatedEntity;
    }
    
    public void refresh(T entity) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        entityManager.refresh(entity);
    }
}

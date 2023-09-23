package br.ucs.classleague.infrastructure.data;

import jakarta.persistence.EntityManager;
import java.io.Serializable;
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
}

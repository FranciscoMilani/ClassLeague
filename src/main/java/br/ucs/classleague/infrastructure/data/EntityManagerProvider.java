package br.ucs.classleague.infrastructure.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerProvider {
    private static final EntityManagerFactory entityManagerFactory;
    private static final EntityManager entityManager;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("classleaguePU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static synchronized EntityManager getEntityManager() {
        return entityManager;
    }
}

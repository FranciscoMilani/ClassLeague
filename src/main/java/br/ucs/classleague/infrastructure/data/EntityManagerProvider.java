package br.ucs.classleague.infrastructure.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerProvider {
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("classleaguePU");
    }

    public static synchronized EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}

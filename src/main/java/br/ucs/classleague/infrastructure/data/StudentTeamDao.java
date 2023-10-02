package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.StudentTeam;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class StudentTeamDao {
    
    public StudentTeam save(StudentTeam entity) {
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
        } finally {
            //entityManager.close();
        }
        
        return null;
    }

}

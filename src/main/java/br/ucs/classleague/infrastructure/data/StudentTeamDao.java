package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.StudentTeam;
import br.ucs.classleague.domain.StudentTeamKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class StudentTeamDao extends GenericDAO<StudentTeam, Long> {

    public StudentTeamDao() {
        super(StudentTeam.class);
    }
    
    public StudentTeam create(StudentTeam entity) {
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
    
    public StudentTeam findByStudentTeamId(StudentTeamKey pk) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();     
        StudentTeam st = entityManager.find(StudentTeam.class, pk);  
        return st;
    }

}

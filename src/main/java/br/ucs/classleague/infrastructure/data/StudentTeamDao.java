package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.Sport.SportsEnum;
import br.ucs.classleague.domain.StudentTeam;
import br.ucs.classleague.domain.StudentTeamKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class StudentTeamDao extends GenericDAO<StudentTeam, StudentTeamKey> {

    public StudentTeamDao() {
        super(StudentTeam.class);
    }
    
    public List<StudentTeam> createAllTeamRelations(List<StudentTeam> entities) {
        List<StudentTeam> persistedEntities = new ArrayList<>();
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        
        try {
            transaction.begin();

            for (StudentTeam entity : entities) {
                entityManager.persist(entity);
                persistedEntities.add(entity);
            }
        
            transaction.commit();
            return persistedEntities;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        
        return null;
    }
    
    public StudentTeam findByStudentTeamId(StudentTeamKey pk) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();     
        StudentTeam st = entityManager.find(StudentTeam.class, pk);  
        return st;
    }
    
    public boolean existsByTeamIdAndSportType(Long studentId, SportsEnum sportType) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager(); 
        String jpql = "SELECT st FROM StudentTeam st WHERE st.student.id = :studentId AND st.sportType = :sportType";

        TypedQuery query = entityManager.createQuery(jpql, StudentTeam.class);
        query.setParameter("studentId", studentId);
        query.setParameter("sportType", sportType);
        
        return !query.getResultList().isEmpty();
    }
}

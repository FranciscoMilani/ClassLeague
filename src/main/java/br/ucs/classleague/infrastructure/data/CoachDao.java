package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.Coach;
import br.ucs.classleague.domain.Sport.SportsEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CoachDao extends GenericDAO<Coach, Long> {
    
    public CoachDao() {
        super(Coach.class);
    }
    
    public List<Coach> findBySport(SportsEnum type) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            String jpql = "SELECT c from Coach c WHERE c.sport = :sport";
            TypedQuery<Coach> query = entityManager.createQuery(jpql, Coach.class);
            query.setParameter("sport", type);
            
            return query.getResultList();     
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.Tournament;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class TournamentDao extends GenericDAO<Tournament, Long> {
    
    public TournamentDao() {
        super(Tournament.class);
    }
    
    public List<Tournament> findByName(String name) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        TypedQuery<Tournament> query = entityManager.createQuery("SELECT t FROM Tournament t WHERE LOWER(t.name) LIKE LOWER(:name)", Tournament.class);
        query.setParameter("name",  "%" + name + "%");
        
        List<Tournament> resultList = query.getResultList();
        
        return resultList;
    }
    
    public List<Tournament> findAllOrderByStartDate() {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        return entityManager.createQuery("SELECT t FROM Tournament t ORDER BY t.startTime ASC", Tournament.class)
            .getResultList();
    }
}
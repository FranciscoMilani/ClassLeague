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
        TypedQuery<Tournament> query = entityManager.createQuery("SELECT t FROM Tournament t WHERE t.name LIKE :name", Tournament.class);
        query.setParameter("name",  "%" + name + "%");
        
        List<Tournament> resultList = query.getResultList();
        
        return resultList;
    }
    
    public void updateTournament(Tournament t){
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
           entityManager.getTransaction().begin();
           entityManager.merge(t);
           entityManager.getTransaction().commit();
        } catch (Exception e) {
        }
    }
}
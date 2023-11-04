package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.Sport.SportsEnum;
import br.ucs.classleague.domain.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


public class TeamDao extends GenericDAO<Team, Long>{
    
    public TeamDao() {
        super(Team.class);
    }

    public List<Team> searchAllTeamsBySport(String sport){
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT i FROM Team i WHERE i.sport = :sport");
            query.setParameter("sport", sport);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public Team searchTeamById(Long id){
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            return entityManager.find(Team.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Team> findBySportIndex(SportsEnum sport){
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            String jpql = "SELECT t from Team t WHERE t.sport = :sport";
            TypedQuery<Team> query = entityManager.createQuery(jpql, Team.class);
            query.setParameter("sport", sport);
            
            return query.getResultList();     
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public Team findByAcronym(String acronym) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            String jpql = "SELECT t from Team t WHERE t.acronym = :acronym";
            TypedQuery<Team> query = entityManager.createQuery(jpql, Team.class);
            query.setParameter("acronym", acronym);
            
            return query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

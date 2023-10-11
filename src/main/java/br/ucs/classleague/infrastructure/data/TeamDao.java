package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
}

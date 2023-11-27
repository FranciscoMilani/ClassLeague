package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.Team;
import br.ucs.classleague.domain.TournamentTeam;
import br.ucs.classleague.domain.TournamentTeamKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;

public class TournamentTeamDao extends GenericDAO<TournamentTeam, TournamentTeamKey>{
    
    public TournamentTeamDao() {
        super(TournamentTeam.class);
    }
    
    public List<TournamentTeam> findByTournamentId(Long id) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        Query query = em.createQuery("SELECT t FROM TournamentTeam t WHERE t.tournament.id = :id ORDER BY t.points DESC");
        query.setParameter("id", id);
        return query.getResultList();
    }
    
    public List<Team> findTeamsByTournamentId(Long id) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        Query query = em.createQuery("SELECT t FROM Team t, TournamentTeam tt " +
                                    "WHERE tt.tournament.id = :id AND t.id = tt.team.id " +
                                    "ORDER BY tt.points DESC");
        query.setParameter("id", id);
        return query.getResultList();
    }
}

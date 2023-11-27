package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.StudentMatch;
import br.ucs.classleague.domain.StudentMatchKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class StudentMatchDao extends GenericDAO<StudentMatch, StudentMatchKey>{
    
    public StudentMatchDao() {
        super(StudentMatch.class);
    }
    
    public List<Object[]> findAllByTournamentId(Long tournamentId, Long teamId) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        
        String jpql =   "SELECT s.name, tt.name, SUM(sm.points) as sum " +
                        "FROM StudentMatch sm " +
                        "JOIN sm.match m " +
                        "JOIN m.tournament t " +
                        "JOIN sm.team tt " +
                        "JOIN sm.student s " +
                        "WHERE t.id = :tournamentId " +
                        (teamId != null ? "AND tt.id = :teamId ": "") +
                        "GROUP BY s.name, tt.name " +
                        "ORDER BY sum DESC, tt.name DESC";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        query.setParameter("tournamentId", tournamentId);
        if (teamId != null) {
            query.setParameter("teamId", teamId);
        }
        
        return query.getResultList();
    }
}
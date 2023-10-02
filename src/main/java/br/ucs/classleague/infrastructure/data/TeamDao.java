package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.Team;


public class TeamDao extends GenericDAO<Team, Long>{
    
    public TeamDao() {
        super(Team.class);
    }

    
}

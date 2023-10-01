package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.Coach;

public class CoachDao extends GenericDAO<Coach, Long> {
    
    public CoachDao() {
        super(Coach.class);
    }
}

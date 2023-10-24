package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.Match;

public class MatchDao extends GenericDAO<Match, Long> {
    
    public MatchDao() {
        super(Match.class);    
    }
}

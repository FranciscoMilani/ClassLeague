package br.ucs.classleague.presentation.model;

import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.MatchTimer;

public class MatchModel extends AbstractModel {
    
    public static final String MATCH = "match";
    
    private Match match;
    private MatchTimer timer;
    private Long tournamentId;
    
    public void setInfo() {
    }

    public Long getMatchId() {
        return match.getId();
    }
    
    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        var oldVal = match;
        this.match = match;
        
        pcs.firePropertyChange(MATCH, oldVal, match);
    }

    public MatchTimer getTimer() {
        return timer;
    }

    public void setTimer(MatchTimer timer) {
        this.timer = timer;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

}

package br.ucs.classleague.infrastructure.presentation.model;

import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.MatchTimer;

public class MatchModel extends AbstractModel {
    
    public static final String MATCH_ID = "match_id";
    
    private Match match;
    private MatchTimer timer;
    private Long tournamentId;
    private Long selectedMatchId;
    
    
    public void setInfo(){
        
    }

    public Long getMatchId() {
        return selectedMatchId;
    }
    
    public void setMatchId(Long matchId) {
        var oldVal = matchId;
        this.selectedMatchId = matchId;
        
        pcs.firePropertyChange(MATCH_ID, oldVal, matchId);
    }
    
    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
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

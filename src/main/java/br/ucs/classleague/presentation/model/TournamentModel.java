package br.ucs.classleague.presentation.model;

import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.Tournament;

public class TournamentModel extends AbstractModel {
   
    public static final String MATCH_STATE = "tournament_matchstate";
    
    private Boolean canStartMatch = false;
    private Tournament tournament = null;
    
    public void checkEnableMatch(Match match) {
        setEnableMatch(!match.getEnded());
    }
    
    public void setEnableMatch(Boolean newVal) {
        var oldVal = canStartMatch;
        canStartMatch = newVal;
        
        pcs.firePropertyChange("tournament_matchstate", (Boolean) oldVal, (Boolean) newVal);
    }
    
    public void setTournament(Tournament newTournament) {
        this.tournament = newTournament;
    }
    
    public Tournament getTournament() {
        return tournament;
    }
    
    public Boolean getCanStartMatch() {
        return canStartMatch;
    }
}

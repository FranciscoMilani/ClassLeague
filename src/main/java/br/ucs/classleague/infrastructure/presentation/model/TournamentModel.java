package br.ucs.classleague.infrastructure.presentation.model;

import br.ucs.classleague.domain.Match;

public class TournamentModel extends AbstractModel {
   
    public static final String MATCH_STATE = "tournament_matchstate";
    public static final String CURRENT_TOURNAMENT = "tournament_currenttournament";
    
    private Boolean canStartMatch = false;
    private Long openedTournamentId = -1L;
    
    public void checkEnableMatch(Match match) {
        if (match.getEnded() != null && match.getEnded()){
            canStartMatch = true;
            pcs.firePropertyChange("tournament_matchstate", (Boolean) canStartMatch, (Boolean)  true);
        } else {
            canStartMatch = false;
            pcs.firePropertyChange("tournament_matchstate", (Boolean) canStartMatch, (Boolean)  false);
        }
    }
    
    public void setTournamentId(Long openedTournamentId) {
        var oldTournament = openedTournamentId;
        this.openedTournamentId = openedTournamentId;
        
        pcs.firePropertyChange("tournament_currenttournament", oldTournament, openedTournamentId);
    }
}

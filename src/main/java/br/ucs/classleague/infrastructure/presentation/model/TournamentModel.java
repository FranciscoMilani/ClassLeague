package br.ucs.classleague.infrastructure.presentation.model;

import br.ucs.classleague.domain.Match;

public class TournamentModel extends AbstractModel {
   
    public static final String MATCH_STATE = "tournament_matchstate";
    public static final String CURRENT_TOURNAMENT = "tournament_currenttournament";
    
    private Boolean canStartMatch = false;
    private Long openedTournamentId = -1L;
    
    public void checkEnableMatch(Match match) {
        // se a partida não terminou, quer dizer que ainda não aconteceu e pode inicia-la
        if (!match.getEnded()){
            setEnableMatch(true);
        } else {
            setEnableMatch(false);
        }
    }
    
    public void setEnableMatch(Boolean newVal){
        var oldVal = canStartMatch;
        canStartMatch = newVal;
        
        pcs.firePropertyChange("tournament_matchstate", (Boolean) oldVal, (Boolean) newVal);
    }
    
    public void setTournamentId(Long newVal) {
        var oldTournament = openedTournamentId;
        openedTournamentId = newVal;
        
        pcs.firePropertyChange("tournament_currenttournament", oldTournament, openedTournamentId);
    }
    
    public Boolean getCanStartMatch() {
        return canStartMatch;
    }

    public Long getOpenedTournamentId() {
        return openedTournamentId;
    }
}

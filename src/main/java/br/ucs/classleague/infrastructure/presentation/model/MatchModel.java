package br.ucs.classleague.infrastructure.presentation.model;

import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.MatchTimer;

public class MatchModel extends AbstractModel {
    
    public static final String MATCH_ID = "match_id";
    
    private Match match;
    private MatchTimer timer;
    private Long tournamentId;
    private Long selectedMatchId;
    
    // Disparar firePropertyChange após mudar as variáveis para a View "ouvir" as mudanças nos métodos desse model
    // Esse método é chamado no Controller. O controller não deve implementar lógica de negócio
    // exemplo:             pcs.firePropertyChange(NOME DA PROPRIEDADE, VALOR ANTIGO, VALOR NOVO)
    // "pcs" é um campo do tipo SwingPropertyChangeSupport declarado na classe pai. 
    // SwingPropertyChangeSupport serve para adicionar listeners da GUI (view) e lançar eventos, implementando assim o padrão de projeto Observer

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

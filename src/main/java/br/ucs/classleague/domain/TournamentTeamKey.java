package br.ucs.classleague.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TournamentTeamKey implements Serializable {
    
    @Column(name = "tournament_id")
    private Long tournamentId;

    @Column(name = "team_id")
    private Long teamId;
    
    public TournamentTeamKey(){
    }

    public TournamentTeamKey(Long tournamentId, Long teamId) {
        this.tournamentId = tournamentId;
        this.teamId = teamId;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    
    
}

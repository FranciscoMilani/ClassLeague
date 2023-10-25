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

    public TournamentTeamKey(Long tournamentId, Long teamId) {
        this.tournamentId = tournamentId;
        this.teamId = teamId;
    }
}

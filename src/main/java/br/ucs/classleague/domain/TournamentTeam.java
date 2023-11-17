package br.ucs.classleague.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity
@Table(name = "tournament_team", uniqueConstraints = {
        @UniqueConstraint(name = "unique_tournament_team", columnNames = {"tournament_id", "team_id"})
})
public class TournamentTeam implements Serializable {
    
    @EmbeddedId
    private TournamentTeamKey tournamentTeamKey;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("tournamentId")
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private Team team;
    
    private Integer points = 0;
    private Integer bracketSeed;

    public TournamentTeam() {
    }
    
    public TournamentTeamKey getTournamentTeamKey() {
        return tournamentTeamKey;
    }

    public void setTournamentTeamKey(TournamentTeamKey tournamentTeamKey) {
        this.tournamentTeamKey = tournamentTeamKey;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
    
    public void addPoints(Integer pointsToAdd) {
        this.points += pointsToAdd;
    }

    public Integer getBracketSeed() {
        return bracketSeed;
    }

    public void setBracketSeed(Integer bracketSeed) {
        this.bracketSeed = bracketSeed;
    }
}

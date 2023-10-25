package br.ucs.classleague.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Match implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany
    @JoinTable(
        joinColumns = @JoinColumn(name = "match_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id"),
        uniqueConstraints = @UniqueConstraint(name = "uc_match_team", columnNames = { "match_id", "team_id" })
    )
    private Set<Team> teams;
    
    private LocalDateTime dateTime;
    
    private Boolean ended;
    
    @ManyToOne
    private Tournament tournament;

    public Match(Tournament tournament, LocalDateTime dateTime, Set<Team> teams) {
        this.tournament = tournament;
        this.dateTime = dateTime;
        this.teams = teams;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    public Team getTeam(int i) {
        List<Team> teamsList = new ArrayList<>(teams);
        return teamsList.get(i);
    }
    
    public void setTeam(Team team) {
        this.teams.add(team);
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Boolean getEnded() {
        return ended;
    }

    public void setEnded(Boolean ended) {
        this.ended = ended;
    }
}

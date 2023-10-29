package br.ucs.classleague.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Match implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Team first_team;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Team second_team;
    
    private LocalDateTime dateTime;
    
    private Boolean ended;
    
    @ManyToOne
    private Tournament tournament;

    public Match() {
    }

    public Match(Tournament tournament, LocalDateTime dateTime, Team t1, Team t2) {
        this.tournament = tournament;
        this.dateTime = dateTime;
        this.first_team = t1;
        this.second_team = t2;
        this.ended = false;
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

    public Team getFirst_team() {
        return first_team;
    }

    public void setFirst_team(Team first_team) {
        this.first_team = first_team;
    }

    public Team getSecond_team() {
        return second_team;
    }

    public void setSecond_team(Team second_team) {
        this.second_team = second_team;
    }

}

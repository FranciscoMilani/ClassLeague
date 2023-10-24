package br.ucs.classleague.domain;

import br.ucs.classleague.domain.Sport.SportsEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Tournament implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Transient
    private Sport sport;

    private LocalDate startTime;
    private LocalDate endTime;
    private String sportString;
    private SportsEnum sportEnum;

    @ManyToMany
    private List<Team> teamsList;

    public Tournament() {
    }

    public Tournament(String name, LocalDate startTime, LocalDate endTime, SportsEnum sport) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sportEnum = sport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sportString;
    }

    public void setSport(Sport sport) {
        this.sportEnum = sport.getSport();
    }

    public void setSport(String sportString) {
        this.sportString = sportString;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public String getSportString() {
        return sportString;
    }

    public void setSportString(String sportString) {
        this.sportString = sportString;
    }

    public SportsEnum getSportEnum() {
        return sportEnum;
    }

    public void setSportEnum(SportsEnum sportEnum) {
        this.sportEnum = sportEnum;
    }

    public List<Team> getTeamsList() {
        return teamsList;
    }

    public void setTeamsList(List<Team> teamsList) {
        this.teamsList = teamsList;
    }
}

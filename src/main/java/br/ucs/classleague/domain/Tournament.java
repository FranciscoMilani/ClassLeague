package br.ucs.classleague.domain;

import br.ucs.classleague.domain.Sport.SportsEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Tournament implements Serializable{

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

    public Tournament() {
    }

    public Tournament(String name, LocalDate startTime, LocalDate endTime, String sport) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sportString = sport;
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
}

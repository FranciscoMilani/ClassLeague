package br.ucs.classleague.domain;

import br.ucs.classleague.domain.Sport.SportsEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Tournament implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date startTime;
    private Date endTime;
    private SportsEnum sportEnum;
    
    @Transient
    private Sport sport;

    public Tournament() {
    }

    public Tournament(String name, Date startTime, Date endTime, SportsEnum sportEnum) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sportEnum = sportEnum;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sportEnum = sport.getSport();
        this.sport = sport;
    }
}

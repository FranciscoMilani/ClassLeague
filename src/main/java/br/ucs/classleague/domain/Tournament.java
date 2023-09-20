package br.ucs.classleague.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Francisco
 */
public class Tournament implements Serializable{

    private Long id;
    private String name;
    private Date startTime;
    private Date endTime;
    private Sport sport;

    public Tournament() {
    }

    public Tournament(String name, Date startTime, Date endTime, Sport sport) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sport = sport;
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
        this.sport = sport;
    }
}

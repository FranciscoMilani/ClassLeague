package br.ucs.classleague.domain;

import java.io.Serializable;

public class MatchResult implements Serializable{

    private Long id;
    private Double points;

    public MatchResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }
}

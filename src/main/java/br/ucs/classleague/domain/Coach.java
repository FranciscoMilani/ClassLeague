package br.ucs.classleague.domain;

/**
 *
 * @author Francisco
 */
public class Coach extends Person {
    private Integer coachId;
    private String sport;

    public Coach() {
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}

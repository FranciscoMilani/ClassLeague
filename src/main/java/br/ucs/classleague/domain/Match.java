package br.ucs.classleague.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Francisco
 */
public class Match implements Serializable{
    private Long id;
    private Date dateTime;

    public Match() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}

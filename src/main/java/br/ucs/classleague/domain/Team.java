package br.ucs.classleague.domain;

import java.io.Serializable;

/**
 *
 * @author Francisco
 */
public class Team implements Serializable{

    private Long id;
    private String name;
    private String acronym;
    private SchoolClass schoolClass;

    public Team() {
    }

    public Team(String name, String acronym, SchoolClass schoolClass) {
        this.name = name;
        this.acronym = acronym;
        this.schoolClass = schoolClass;
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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }
}

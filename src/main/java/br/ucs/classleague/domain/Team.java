package br.ucs.classleague.domain;

/**
 *
 * @author Francisco
 */
public class Team {
    private String name;
    private String acronym;
    private Class schoolClass;

    public Team(String name, String acronym, Class schoolClass) {
        this.name = name;
        this.acronym = acronym;
        this.schoolClass = schoolClass;
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

    public Class getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(Class schoolClass) {
        this.schoolClass = schoolClass;
    }
}

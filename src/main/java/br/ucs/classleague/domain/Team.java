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
    
    
}

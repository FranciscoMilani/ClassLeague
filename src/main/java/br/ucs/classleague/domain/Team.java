package br.ucs.classleague.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String acronym;
    @ManyToOne
    private SchoolClass schoolClass;
    @OneToOne
    private Sport sport;
    @OneToMany(mappedBy = "team")
    private Set<StudentTeam> studentTeam;

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

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Set<StudentTeam> getStudentTeam() {
        return studentTeam;
    }

    public void setStudentTeam(Set<StudentTeam> studentTeam) {
        this.studentTeam = studentTeam;
    }

    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", name=" + name + ", acronym=" + acronym + ", schoolClass=" + schoolClass + ", sport=" + sport + ", studentTeam=" + studentTeam + '}';
    }
}

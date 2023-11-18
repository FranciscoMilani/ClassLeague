package br.ucs.classleague.domain;

import br.ucs.classleague.domain.Sport.SportsEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @Column(unique = true)
    private String acronym;
    
    @ManyToOne
    private SchoolClass schoolClass;
    private SportsEnum sport;
    
    @OneToMany(mappedBy = "team")
    private Set<StudentTeam> studentTeam;
    
    @OneToMany(mappedBy = "team")
    private Set<TournamentTeam> TournamentTeam;

    public Team() {
    }

    public Team(String name, String acronym, SportsEnum sport, SchoolClass schoolClass) {
        this.name = name;
        this.acronym = acronym;
        this.sport = sport;
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
    
    public Set<StudentTeam> getStudentTeam() {
        return studentTeam;
    }

    public void setStudentTeam(Set<StudentTeam> studentTeam) {
        this.studentTeam = studentTeam;
    }

    public SportsEnum getSport() {
        return sport;
    }

    public void setSport(SportsEnum sport) {
        this.sport = sport;
    }

    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", name=" + name + ", acronym=" + acronym + ", schoolClass=" + schoolClass + ", sport=" + sport + ", studentTeam=" + studentTeam + '}';
    }

    public Sport.SportsEnum getSport() {
        return sport;
    }

    public void setSport(Sport.SportsEnum sport) {
        this.sport = sport;
    }
}

package br.ucs.classleague.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "student_match")
public class StudentMatch implements Serializable {

    @EmbeddedId
    private StudentMatchKey studentMatchKey;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("matchId")
    @JoinColumn(name = "match_id")
    private Match match;
    
    @ManyToOne
    private Team team;
    
    private Integer points = 0;

    public StudentMatch() {
    }
    
    public StudentMatch(Student student, Match match, Team team) {
        this.student = student;
        this.match = match;
        this.team = team;
    }

    public StudentMatchKey getStudentMatchKey() {
        return studentMatchKey;
    }

    public void setStudentMatchKey(StudentMatchKey studentMatchKey) {
        this.studentMatchKey = studentMatchKey;
    }

    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }

    public Match getMatch() {
        return match;
    }
    
    public void setMatch(Match match) {
        this.match = match;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}

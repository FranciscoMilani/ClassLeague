package br.ucs.classleague.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity
@Table(name = "student_team", uniqueConstraints = {
        @UniqueConstraint(name = "unique_student_team", columnNames = {"student_id", "team_id"})
})
public class StudentTeam implements Serializable {

    @EmbeddedId
    private StudentTeamKey studentTeamKey;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private Team team;
    
    private int points;
    private String position;

    public StudentTeamKey getStudentTeamKey() {
        return studentTeamKey;
    }

    public void setStudentTeamKey(StudentTeamKey studentTeamKey) {
        this.studentTeamKey = studentTeamKey;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
}

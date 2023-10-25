package br.ucs.classleague.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StudentTeamKey implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "team_id")
    private Long teamId;
    
    public StudentTeamKey(Long studentId, Long teamId) {
        this.studentId = studentId;
        this.teamId = teamId;
    }
}
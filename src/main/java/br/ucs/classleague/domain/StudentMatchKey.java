package br.ucs.classleague.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StudentMatchKey implements Serializable {
    
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "match_id")
    private Long matchId;
    
    public StudentMatchKey(){
    }

    public StudentMatchKey(Long studentId, Long matchId) {
        this.studentId = studentId;
        this.matchId = matchId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }
}

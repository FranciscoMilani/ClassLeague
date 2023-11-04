package br.ucs.classleague.application.Services;

import br.ucs.classleague.domain.Student;
import br.ucs.classleague.domain.StudentTeam;
import br.ucs.classleague.domain.StudentTeamKey;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.infrastructure.data.DaoFactory;
import br.ucs.classleague.infrastructure.data.MatchDao;
import br.ucs.classleague.infrastructure.data.StudentTeamDao;
import br.ucs.classleague.infrastructure.data.TeamDao;
import java.util.ArrayList;
import java.util.List;


public class MatchService {
    private MatchDao matchDao;
    private TeamDao teamDao;
    private StudentTeamDao studentTeamDao;

    public MatchService() {
        this.matchDao = DaoFactory.getMatchDao();
        this.teamDao = DaoFactory.getTeamDao();
        this.studentTeamDao = DaoFactory.getStudentTeamDao();
    }
    
    public List<Object[]> teamStudentsToObjectArray(String teamAcronym) {
        Team t = teamDao.findByAcronym(teamAcronym);
        List<StudentTeam> studentTeam = new ArrayList<>(t.getStudentTeam());
        List<Object[]> students = new ArrayList<>();
        
        for (StudentTeam st : studentTeam) {
            Student s = st.getStudent();
            students.add(new Object[] {
                s.getId(),
                s.getName(),
                st.getPoints()
            });
        }
        
        return students;
    }
    
//    public updatePointsForTeam(String teamAcronym) {;
//        
//    }
//    
    public int updatePointsForPlayer(Integer pointsAmount, Long studentId, Long teamId) {
        StudentTeam st = studentTeamDao.findByStudentTeamId(new StudentTeamKey(studentId, teamId));
        st.setPoints(st.getPoints() + pointsAmount);
        studentTeamDao.update(st);
        return st.getPoints();
    }
    
    public Team getTeamWithAcronym(String acronym) {
        return teamDao.findByAcronym(acronym);
    }
}

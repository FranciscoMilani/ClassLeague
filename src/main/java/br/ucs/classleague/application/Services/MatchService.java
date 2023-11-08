package br.ucs.classleague.application.Services;

import br.ucs.classleague.domain.Match;
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

    public int updatePointsForPlayer(Integer pointsAmount, Long studentId, Long teamId) {
        StudentTeam st = studentTeamDao.findByStudentTeamId(new StudentTeamKey(studentId, teamId));
        Integer score = Math.max(0, st.getPoints() + pointsAmount);
        st.setPoints(score);
        studentTeamDao.update(st);
        return st.getPoints();
    }
    
    public int updatePointsForTeam(Match match, Integer teamIndex, Integer pointsAmount) {
        int sum = 0;
        if (teamIndex == 0) {
            sum = Math.max(0, match.getFirst_team_score() + pointsAmount);
            match.setFirst_team_score(sum);
        } else if (teamIndex == 1) {
            sum = Math.max(0, match.getSecond_team_score() + pointsAmount);
            match.setSecond_team_score(sum);
        }
        
        matchDao.update(match);
        return sum;
    }
    
    public Team getTeamWithAcronym(String acronym) {
        return teamDao.findByAcronym(acronym);
    }
    
    public Team determineMatchWinner(Match match) {
        int firstScore = match.getFirst_team_score();
        int secondScore = match.getSecond_team_score();
        
        if (firstScore > secondScore) {
            match.setWinner(match.getFirst_team());
        } else if (firstScore < secondScore) {
            match.setWinner(match.getSecond_team());
        } else {
            // empate
            return null;
        }

        matchDao.update(match);
        return (firstScore > secondScore) ? match.getFirst_team() : match.getSecond_team();
    }
}

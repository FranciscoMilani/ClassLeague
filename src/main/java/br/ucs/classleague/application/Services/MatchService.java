package br.ucs.classleague.application.Services;

import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.Student;
import br.ucs.classleague.domain.StudentTeam;
import br.ucs.classleague.domain.StudentTeamKey;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.domain.TournamentTeam;
import br.ucs.classleague.domain.TournamentTeamKey;
import br.ucs.classleague.infrastructure.data.DaoProvider;
import br.ucs.classleague.infrastructure.data.MatchDao;
import br.ucs.classleague.infrastructure.data.StudentTeamDao;
import br.ucs.classleague.infrastructure.data.TeamDao;
import br.ucs.classleague.infrastructure.data.TournamentTeamDao;
import java.util.ArrayList;
import java.util.List;


public class MatchService {
    private MatchDao matchDao;
    private TeamDao teamDao;
    private StudentTeamDao studentTeamDao;
    private TournamentTeamDao ttDao;

    public MatchService() {
        this.matchDao = DaoProvider.getMatchDao();
        this.teamDao = DaoProvider.getTeamDao();
        this.studentTeamDao = DaoProvider.getStudentTeamDao();
        this.ttDao = DaoProvider.getTournamentTeamDao();
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
            TournamentTeamKey key1 = new TournamentTeamKey(match.getTournament().getId(), match.getFirst_team().getId());
            TournamentTeam team1 = ttDao.findById(key1).get();
            team1.addPoints(pointsAmount);
            ttDao.update(team1);
            match.setFirst_team_score(sum);
        } else if (teamIndex == 1) {
            sum = Math.max(0, match.getSecond_team_score() + pointsAmount);
             TournamentTeamKey key2 = new TournamentTeamKey(match.getTournament().getId(), match.getSecond_team().getId());
            TournamentTeam team2 = ttDao.findById(key2).get();
            team2.addPoints(pointsAmount);
            ttDao.update(team2);
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
    
    public void updateOverallTeamScore(Match match, int team1Points, int team2Points) {
        Long tId = match.getTournament().getId();
        TournamentTeamKey key1 = new TournamentTeamKey(tId, match.getFirst_team().getId());
        TournamentTeamKey key2 = new TournamentTeamKey(tId, match.getSecond_team().getId());
        TournamentTeam team1 = ttDao.findById(key1).get();
        TournamentTeam team2 = ttDao.findById(key2).get();
        team1.addPoints(team1Points);
        team2.addPoints(team2Points);
        ttDao.update(team1);
        ttDao.update(team2);
    }
}

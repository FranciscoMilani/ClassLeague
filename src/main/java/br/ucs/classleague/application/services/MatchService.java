package br.ucs.classleague.application.services;

import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.Student;
import br.ucs.classleague.domain.StudentMatch;
import br.ucs.classleague.domain.StudentMatchKey;
import br.ucs.classleague.domain.StudentTeam;
import br.ucs.classleague.domain.StudentTeamKey;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.domain.TournamentTeam;
import br.ucs.classleague.domain.TournamentTeamKey;
import br.ucs.classleague.infrastructure.data.DaoProvider;
import br.ucs.classleague.infrastructure.data.MatchDao;
import br.ucs.classleague.infrastructure.data.StudentDao;
import br.ucs.classleague.infrastructure.data.StudentMatchDao;
import br.ucs.classleague.infrastructure.data.StudentTeamDao;
import br.ucs.classleague.infrastructure.data.TeamDao;
import br.ucs.classleague.infrastructure.data.TournamentTeamDao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MatchService {
    private MatchDao matchDao;
    private TeamDao teamDao;
    private StudentTeamDao studentTeamDao;
    private TournamentTeamDao ttDao;
    private StudentMatchDao smDao;
    private StudentDao sDao;

    public MatchService() {
        this.matchDao = DaoProvider.getMatchDao();
        this.teamDao = DaoProvider.getTeamDao();
        this.studentTeamDao = DaoProvider.getStudentTeamDao();
        this.ttDao = DaoProvider.getTournamentTeamDao();
        this.smDao = DaoProvider.getStudentMatchDao();
        this.sDao = DaoProvider.getStudentDao();
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

    public int updatePointsForPlayer(Integer pointsAmount, Long studentId, Long teamId, Long matchId) {
        StudentTeam st = studentTeamDao.findByStudentTeamId(new StudentTeamKey(studentId, teamId));
        Student s = st.getStudent();
        StudentMatchKey smk = new StudentMatchKey(studentId, matchId);
        StudentMatch sm = smDao.findById(smk).get();
        
        Integer matchScore = Math.max(0, st.getPoints() + pointsAmount);
        Integer overallPoints = Math.max(0, s.getPontos() + pointsAmount);
        Integer tournamentPoints = Math.max(0, sm.getPoints() + pointsAmount);
        
        st.setPoints(matchScore);
        s.setPontos(overallPoints);
        sm.setPoints(tournamentPoints);
        
        studentTeamDao.update(st);
        sDao.update(s);
        smDao.update(sm);
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

        match.setEnded(true);
        matchDao.update(match);
        return (firstScore > secondScore) ? match.getFirst_team() : match.getSecond_team();
    }
    
    public void setDrawedMatchWinner(Match match, Integer winnerTeamIndex) {
        if (winnerTeamIndex == 0) {
            match.setWinner(match.getFirst_team());
        } else {
            match.setWinner(match.getSecond_team());
        }

        match.setEnded(true);
        matchDao.update(match);
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
    
    public void registerTeamStudentsForMatch(Match match, Team[] teams) {
        
        for (Team team : teams) {
            List<StudentTeam> studentsTeam = new ArrayList<>(team.getStudentTeam());
            List<Student> students = studentsTeam.stream()
                    .map(StudentTeam::getStudent)
                    .collect(Collectors.toList());
            
            for (Student student : students) {
                StudentMatch studentMatch = new StudentMatch();
                StudentMatchKey smKey = new StudentMatchKey(
                        match.getId(), 
                        student.getId()
                );

                studentMatch.setStudentMatchKey(smKey);
                studentMatch.setMatch(match);
                studentMatch.setStudent(student);
                studentMatch.setTeam(team);
                
                smDao.create(studentMatch);
            } 
        }
        
    }
}

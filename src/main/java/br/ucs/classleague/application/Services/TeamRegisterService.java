package br.ucs.classleague.application.Services;

import br.ucs.classleague.domain.Coach;
import br.ucs.classleague.domain.Sport;
import br.ucs.classleague.domain.StudentTeam;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.infrastructure.data.CoachDao;
import br.ucs.classleague.infrastructure.data.DaoProvider;
import br.ucs.classleague.infrastructure.data.StudentTeamDao;
import br.ucs.classleague.infrastructure.data.TeamDao;
import java.util.List;

public class TeamRegisterService {
    private TeamDao teamDao;
    private StudentTeamDao studentTeamDao;
    private CoachDao coachDao;

    public TeamRegisterService() {
        this.teamDao = DaoProvider.getTeamDao();
        this.studentTeamDao = DaoProvider.getStudentTeamDao(); 
        this.coachDao = DaoProvider.getCoachDao();
    }
    
    public String[] getSportsNames(){
        String[] names = new String[Sport.SportsEnum.values().length];

        for (int i = 0; i < Sport.SportsEnum.values().length; i++){
            names[i] = Sport.SportsEnum.values()[i].getName();
        }

        return names;
    }
    
    public Long registerTeam(Team team){
        return teamDao.create(team).getId();
    }
    
    public void registerStudentsForTeam(List<StudentTeam> stList){
        for (StudentTeam st : stList) {
            studentTeamDao.create(st);
        }
    }
    
    public StudentTeam registerStudentForTeam(StudentTeam st){
        return studentTeamDao.create(st);
    }
    
    public Object[] getCoachesToArray(int sportIndex) {
        List<Coach> coachesList = coachDao.findBySport(Sport.SportsEnum.values()[sportIndex]);
        
        if (coachesList != null) {
            return coachesList.toArray();   
        } else {
            return null;
        }
    }
}

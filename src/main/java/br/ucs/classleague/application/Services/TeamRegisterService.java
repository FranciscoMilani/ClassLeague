package br.ucs.classleague.application.Services;

import br.ucs.classleague.domain.Sport;
import br.ucs.classleague.domain.StudentTeam;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.infrastructure.data.DaoFactory;
import br.ucs.classleague.infrastructure.data.StudentTeamDao;
import br.ucs.classleague.infrastructure.data.TeamDao;
import java.util.List;

public class TeamRegisterService {
    private TeamDao teamDao;
    private StudentTeamDao studentTeamDao;

    public TeamRegisterService() {
        this.teamDao = DaoFactory.getTeamDao();
        this.studentTeamDao = DaoFactory.getStudentTeamDao(); 
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
}

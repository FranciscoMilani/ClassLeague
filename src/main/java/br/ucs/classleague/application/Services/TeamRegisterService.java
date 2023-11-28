package br.ucs.classleague.application.services;

import br.ucs.classleague.domain.Coach;
import br.ucs.classleague.domain.Sport;
import br.ucs.classleague.domain.Sport.SportsEnum;
import br.ucs.classleague.domain.Student;
import br.ucs.classleague.domain.StudentTeam;
import br.ucs.classleague.domain.StudentTeamKey;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.infrastructure.data.CoachDao;
import br.ucs.classleague.infrastructure.data.DaoProvider;
import br.ucs.classleague.infrastructure.data.StudentDao;
import br.ucs.classleague.infrastructure.data.StudentTeamDao;
import br.ucs.classleague.infrastructure.data.TeamDao;
import java.util.ArrayList;
import java.util.List;

public class TeamRegisterService {
    private TeamDao teamDao;
    private StudentTeamDao studentTeamDao;
    private CoachDao coachDao;
    private StudentDao studentDao;

    public TeamRegisterService() {
        this.teamDao = DaoProvider.getTeamDao();
        this.studentTeamDao = DaoProvider.getStudentTeamDao(); 
        this.coachDao = DaoProvider.getCoachDao();
        this.studentDao = DaoProvider.getStudentDao();
    }
    
    public String[] getSportsNames(){
        String[] names = new String[Sport.SportsEnum.values().length];

        for (int i = 0; i < Sport.SportsEnum.values().length; i++){
            names[i] = Sport.SportsEnum.values()[i].getName();
        }

        return names;
    }
    
    public boolean registerStudentsForTeam(List<StudentTeam> stList){
        List<StudentTeam> result = studentTeamDao.createAllTeamRelations(stList);      
        return result == null ? false : true;
    }
    
    public Object[] getCoachesToArray(int sportIndex) {
        List<Coach> coachesList = coachDao.findBySport(Sport.SportsEnum.values()[sportIndex]);
        
        if (coachesList != null) {
            return coachesList.toArray();   
        } else {
            return null;
        }
    }
    
    public boolean assignTeamMembers(List<Long> memberIds, Long teamId) {
        Team team = teamDao.findById(teamId).get();
        List<StudentTeam> stList = new ArrayList<>();
        
        for (Long id : memberIds) {
            StudentTeam st = new StudentTeam();
            Student student = studentDao.findById(id).get();

            st.setStudentTeamKey(new StudentTeamKey(id, teamId));
            st.setStudent(student);
            st.setTeam(team);
            
            stList.add(st);
        }
        
        return registerStudentsForTeam(stList);
    }
    
    public boolean verifyAvailableStudent(Long studentId, SportsEnum sportType) {
        boolean exists = studentTeamDao.existsByTeamIdAndSportType(studentId, sportType);
        if (exists) {
            System.out.println("Estudante já inserido no time");
            return false;
        } 
        
        return true;
    }
    
    public boolean verifyAvailableAcronym(String acronym) {
        var result = teamDao.existsByAcronym(acronym);
        if (result) {
            System.out.println("Acronimo já cadastrado");
            return false;
        } 
        
        return true;
    }
}

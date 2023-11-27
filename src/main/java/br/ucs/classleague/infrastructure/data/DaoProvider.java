package br.ucs.classleague.infrastructure.data;

public class DaoProvider {
    
    // Declarar DAOs
    //-----------------------------------
    private static ClassDao classDao;
    private static StudentDao studentDao;
    private static TeamDao teamDao;
    private static StudentTeamDao studentTeamDao;
    private static TournamentDao tournamentDao;
    private static MatchDao matchDao;
    private static TournamentTeamDao tournamentTeamDao;
    private static CoachDao coachDao;
    private static StudentMatchDao studentMatchDao;
    
    static {
        classDao = new ClassDao();
        studentDao = new StudentDao();
        teamDao = new TeamDao();
        studentTeamDao = new StudentTeamDao();
        tournamentDao = new TournamentDao();
        matchDao = new MatchDao();
        tournamentTeamDao = new TournamentTeamDao();
        coachDao = new CoachDao();
        studentMatchDao = new StudentMatchDao();
    }
    
    public static ClassDao getClassDao() {
        return classDao;
    }
    
    public static StudentDao getStudentDao() {
        return studentDao;
    }
    
    public static TeamDao getTeamDao() {
        return teamDao;
    }
    
    public static StudentTeamDao getStudentTeamDao(){
        return studentTeamDao;
    }
    
    public static TournamentDao getTournamentDao() {
        return tournamentDao;
    }
    
    public static MatchDao getMatchDao() {
        return matchDao;
    }

    public static TournamentTeamDao getTournamentTeamDao() {
        return tournamentTeamDao;
    }

    public static CoachDao getCoachDao() {
        return coachDao;
    }

    public static StudentMatchDao getStudentMatchDao() {
        return studentMatchDao;
    }
}

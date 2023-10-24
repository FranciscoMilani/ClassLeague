package br.ucs.classleague.infrastructure.data;

public class DaoFactory {
    
    // Declarar DAOs
    //-----------------------------------
    private static ClassDao classDao;
    private static StudentDao studentDao;
    private static TeamDao teamDao;
    private static StudentTeamDao studentTeamDao;
    private static TournamentDao tournamentDao;
    private static MatchDao matchDao;
    
    static {
        classDao = new ClassDao();
        studentDao = new StudentDao();
        teamDao = new TeamDao();
        studentTeamDao = new StudentTeamDao();
        tournamentDao = new TournamentDao();
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
}

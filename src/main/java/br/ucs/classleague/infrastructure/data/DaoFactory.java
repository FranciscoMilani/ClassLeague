package br.ucs.classleague.infrastructure.data;

public class DaoFactory {
    
    // Declarar DAOs
    //-----------------------------------
    
    private static ClassDao classDao;
    private static StudentDao studentDao;
    
    //-----------------------------------
    
    static {
        classDao = new ClassDao();
        studentDao = new StudentDao();
    }
    
    public static ClassDao getClassDao() {
        return classDao;
    }
    
    public static StudentDao getStudentDao() {
        return studentDao;
    }
}

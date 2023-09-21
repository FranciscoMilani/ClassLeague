package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.domain.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datasource {
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("classleague");
    private EntityManager em = emf.createEntityManager();
    
    private final String url = "jdbc:postgresql://localhost/classleague";
    private final String user = "postgres";
    private final String password = "postgres";
    
    public Datasource(){
        em.persist(new SchoolClass("CETEC", SchoolClass.SchoolShift.FULL_TIME, 2, SchoolClass.EducationalCycle.HIGH_SCHOOL));
        em.find(SchoolClass.class, 1);
        em.close();
        Connection conn = this.getConnection(url, user, password);
    }
    
    private Connection getConnection(String url, String user, String password){
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}


package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.domain.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ClassDao extends GenericDAO<SchoolClass, Long>{
    
    public ClassDao() {
        super(SchoolClass.class);
    }
    
    public List<Student> getStudentsByClassNumber(int schoolClassNumber) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        
        try {
            String jpql = "SELECT s from Student s WHERE s.schoolClass.number = :schoolClassNumber";
            TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
            query.setParameter("schoolClassNumber", schoolClassNumber);
            
            return query.getResultList();
        } finally {
            //entityManager.close();
        }
    }
    
    public SchoolClass findByNumber(int schoolClassNumber) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        
        try {
            String jpql = "SELECT sc from SchoolClass sc WHERE sc.number = :schoolClassNumber";
            TypedQuery<SchoolClass> query = entityManager.createQuery(jpql, SchoolClass.class);
            query.setParameter("schoolClassNumber", schoolClassNumber);
            
            return query.getSingleResult();
        } finally {
            //entityManager.close();
        }
    }
}

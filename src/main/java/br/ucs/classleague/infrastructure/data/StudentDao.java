package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class StudentDao extends GenericDAO<Student, Long> {
    
    public StudentDao() {
        super(Student.class);
    }
    
    public boolean existsStudentCPF(String cpf) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            String jpql = "SELECT COUNT(s) FROM students s WHERE s.cpf = :cpf";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("cpf", cpf);

            Long count = query.getSingleResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }    
}


package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.SchoolClass;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class ClassDao {
    
    public ClassDao() {

    }
    
    public static Optional<SchoolClass> findById(Long id) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        
        try {
            return Optional.ofNullable(entityManager.find(SchoolClass.class, id));
        } finally {
            entityManager.close();
        }
    }
    
    public static List<String> findAll(){
         EntityManager entityManager = EntityManagerProvider.getEntityManager();
         List<String> names = entityManager.createQuery("SELECT c.name FROM SchoolClass c", String.class).getResultList();
         return names;
    }
}

package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.SchoolClass;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ClassDao extends GenericDAO<SchoolClass, Long>{
    
    private EntityManager em;

    public ClassDao() {
        super(SchoolClass.class);
    }
    
//    public SchoolClass findById(Long id){
//        return em.find(SchoolClass.class, id);
//    }
}


package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.SchoolClass;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class ClassDao extends GenericDAO<SchoolClass, Long>{
    
    public ClassDao() {
        super(SchoolClass.class);
    }
}

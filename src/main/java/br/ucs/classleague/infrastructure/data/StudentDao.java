package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.Student;
import java.io.Serializable;

public class StudentDao extends GenericDAO<Student, Long> {
    
    public StudentDao() {
        super(Student.class);
    }
    
}

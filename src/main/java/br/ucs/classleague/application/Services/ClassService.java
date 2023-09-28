package br.ucs.classleague.application.Services;

import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.data.DaoFactory;

public class ClassService {
    private ClassDao classDao;
    
    public ClassService(){
        this.classDao = DaoFactory.getClassDao();
    }
    
    public void registerClass(SchoolClass schoolClass){
        classDao.create(schoolClass);
    }
}

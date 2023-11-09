package br.ucs.classleague.application.Services;

import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.data.DaoProvider;

public class ClassService {
    private ClassDao classDao;
    
    public ClassService(){
        this.classDao = DaoProvider.getClassDao();
    }
    
    public void registerClass(SchoolClass schoolClass){
        classDao.create(schoolClass);
    }
}

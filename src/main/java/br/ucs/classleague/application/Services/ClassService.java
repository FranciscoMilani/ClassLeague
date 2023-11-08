package br.ucs.classleague.application.Services;

import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.data.DaoFactory;

public class ClassService {

    private ClassDao classDao;

    public ClassService() {
        this.classDao = DaoFactory.getClassDao();
    }

    public void registerClass(SchoolClass schoolClass) {
        classDao.create(schoolClass);
    }

    public String[] getClassShiftNames() {
        String[] names = new String[SchoolClass.SchoolShift.values().length];

        for (int i = 0; i < SchoolClass.SchoolShift.values().length; i++) {
            names[i] = SchoolClass.SchoolShift.values()[i].getName();
        }
        return names;
    }

    public String[] getEducationalCycleNames() {
        String[] names = new String[SchoolClass.EducationalCycle.values().length];

        for (int i = 0; i < SchoolClass.EducationalCycle.values().length; i++) {
            names[i] = SchoolClass.EducationalCycle.values()[i].getName();
        }
        return names;
    }

    
}

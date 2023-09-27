package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.domain.Student;
import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.data.StudentDao;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class RegisterController {
    
    private GUI frame;
    private ClassDao classDao = new ClassDao();
    private StudentDao studentDao = new StudentDao();
    
    public RegisterController(GUI frame){
        this.frame = frame;
    }
    
    public void showClassesNames(){
        DefaultComboBoxModel model = new DefaultComboBoxModel<String>();
        System.out.println(classDao.findAll());
        List<String> names = classDao.findAll()
                .stream()
                .map(SchoolClass::getName)
                .collect(Collectors.toList());
        
        model.addAll(names);
        frame.jStudentClassComboBox.setModel(model);
    }
    
    public Boolean registerStudent(){
        
        //Cria um objeto da classe schoolclass para informar no cadastro do aluno
        SchoolClass schoolClass = new SchoolClass(
                "",
                0,
                null,
                null  
        );
        
        Student student = new Student(
                null, 
                frame.jStudentFatherNameField.getText(), 
                frame.jStudentMotherNameField.getText(), 
                0, 
                frame.jStudentNameField.getText(),
                "",
                null, 
                frame.jStudentSexField.getText(),
                "" ,
                "",
                null
        );
        
        studentDao.create(student);
      
        return true;
    }
    
    public Boolean registerClass(){
        int shiftIndex = frame.jClassShift.getSelectedIndex();
        int cycleIndex = frame.jClassCycle.getSelectedIndex();
        
        SchoolClass schoolClass = new SchoolClass(
                frame.jClassName.getText(),
                Integer.parseInt(frame.jClassNumber.getText()),
                SchoolClass.SchoolShift.values()[shiftIndex],
                SchoolClass.EducationalCycle.values()[cycleIndex]  
        );
        
        classDao.create(schoolClass);
        return true;
    }
}

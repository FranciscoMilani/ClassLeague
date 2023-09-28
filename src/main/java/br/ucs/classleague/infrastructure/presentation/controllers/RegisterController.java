package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.application.Services.ClassService;
import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.domain.Student;
import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.data.StudentDao;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class RegisterController {
    
    private GUI frame;
    private ClassDao classDao = new ClassDao();
    private StudentDao studentDao = new StudentDao();
    private ClassService classService = new ClassService();
    
    public RegisterController(GUI frame){
        this.frame = frame;
    }
    
    public void showClassesNumbers(JComboBox comboBox) {
        DefaultComboBoxModel model = new DefaultComboBoxModel<String>();
        List<Integer> numbers = classDao.findAll()
                .stream()
                .map(SchoolClass::getNumber)
                .collect(Collectors.toList());
        
        model.addAll(numbers);
        comboBox.setModel(model);
    }
    
    public Boolean registerStudent(){
        return null;
//        Student student = new Student(
//        frame.jTextField8,      
//        );
//        studentDao.create();
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
        
        classService.registerClass(schoolClass);
       
        return true;
    }
    
    private void resetTeamTable() {
        JTable table = frame.jTable1;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        table.revalidate();
    }
    
    public DefaultTableModel getTableModel(int rowCount) {
        String[] columnHeaders = new String[] {"ID", "Nome", ""};

        DefaultTableModel teamRegisterTableModel = new DefaultTableModel(columnHeaders, rowCount) {

            // Apenas última coluna editável (checkbox)
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }

            // Cria colunas de tipos diferentes (última é Bool p/ checkbox)
            public Class<?> getColumnClass(int column) {
                if (column == 2)
                    return Boolean.class;
                else
                    return String.class;
            }
        };

        return teamRegisterTableModel;
    }
    
    public void updateTeamTableCells(String number) {
        List<Student> students;
        
        try {
            Integer n = Integer.parseInt(number);
            
            if (n == frame.prevClassNumber) {
                return; 
            }
            
            students = new ArrayList<>();
            frame.prevClassNumber = n;
            
            resetTeamTable();
            students = classDao.getStudentsByClassNumber(n);
        } catch (Exception e){
            e.printStackTrace();
            return;
        }
        
        DefaultTableModel model = getTableModel(students.size());
        
        frame.jTable1.setModel(model);
        
        for (int i = 0; i < students.size(); i++) {
            System.out.println(students.get(i).toString());
            model.setValueAt(students.get(i).getId(), i, 0);
            model.setValueAt(students.get(i).getName(), i, 1);
        }
    }
}

package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.application.Services.ClassService;
import br.ucs.classleague.application.Services.TeamRegisterService;
import br.ucs.classleague.domain.Coach;
import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.domain.Sport;
import br.ucs.classleague.domain.Student;
import br.ucs.classleague.domain.StudentTeam;
import br.ucs.classleague.domain.StudentTeamKey;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.data.CoachDao;
import br.ucs.classleague.infrastructure.data.EntityManagerProvider;
import br.ucs.classleague.infrastructure.data.StudentDao;
import br.ucs.classleague.infrastructure.data.StudentTeamDao;
import br.ucs.classleague.infrastructure.data.TeamDao;
import br.ucs.classleague.infrastructure.data.TournamentDao;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RegisterController {
    
    private GUI frame;
    private ClassDao classDao = new ClassDao();
    private StudentDao studentDao = new StudentDao();
    private TeamDao teamDao = new TeamDao();
    private ClassService classService = new ClassService();
    private CoachDao coachDao = new CoachDao();
    private TeamRegisterService teamRegisterService = new TeamRegisterService();
    private StudentTeamDao studentTeamDao = new StudentTeamDao();
    private TournamentDao tournamentDao = new TournamentDao();
    
    public RegisterController(GUI frame){
        this.frame = frame;
    }
    
    public void showClassesNumbers() {
        JComboBox comboBox = frame.jRegisterStudentClassComboBox;
        JComboBox comboBoxTeam = frame.jTeamRegisterClassPickerComboBox;
        
        DefaultComboBoxModel model = new DefaultComboBoxModel<String>();
        List<Integer> numbers = classDao.findAll()
                .stream()
                .map(SchoolClass::getNumber)
                .collect(Collectors.toList());
        
        model.addAll(numbers);
        comboBox.setModel(model);
        comboBoxTeam.setModel(model);
    }
    
    public Boolean registerStudent(){
        int classNumber = Integer.parseInt(frame.jRegisterStudentClassComboBox.getSelectedItem().toString());
        SchoolClass schoolClass = classDao.findByNumber(classNumber);
        
        Student student = new Student(
                schoolClass, 
                frame.jRegisterStudentFatherNameField.getText(), 
                frame.jRegisterStudentMotherNameField.getText(), 
                0, 
                frame.jRegisterStudentNameField.getText(),
                "",
                LocalDate.now(), 
                frame.jRegisterStudentGenderField.getText(),
                "" ,
                ""
        );
        
        studentDao.create(student);
      
        return true;
    }
    
    public Boolean registerClass(){
        int shiftIndex = frame.jClassShift.getSelectedIndex();
        int cycleIndex = frame.jClassCycle.getSelectedIndex();
        
        SchoolClass schoolClass = new SchoolClass(
            frame.jClassNameField.getText(),
            Integer.parseInt(frame.jClassNumber.getText()),
            SchoolClass.SchoolShift.values()[shiftIndex],
            SchoolClass.EducationalCycle.values()[cycleIndex]  
        );
        
        classService.registerClass(schoolClass);
       
        return true;
    }
    
    private void resetTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        table.revalidate();
    }
    
    public DefaultTableModel getTeamRegisterTableModel(int rowCount) {
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
            
            resetTable(frame.jTeamRegisterStudentsTable);
            students = classDao.getStudentsByClassNumber(n);
        } catch (Exception e){
            e.printStackTrace();
            return;
        }
        
        DefaultTableModel model = getTeamRegisterTableModel(students.size());
        
        frame.jTeamRegisterStudentsTable.setModel(model);
        
        for (int i = 0; i < students.size(); i++) {
            System.out.println(students.get(i).toString());
            model.setValueAt(students.get(i).getId(), i, 0);
            model.setValueAt(students.get(i).getName(), i, 1);
        }
    }
    
    public String[] showSportsNames(){
        return teamRegisterService.getSportsNames();
    }
    
    public void registerTeam(JTable table) {
        String name = frame.jTeamRegisterNameField.getText();
        String acronym = frame.jTeamRegisterAcronymField.getText();
        String classString = frame.jTeamRegisterClassPickerComboBox.getSelectedItem().toString();
        int sportEnumIndex = frame.jTeamRegisterSportComboBox.getSelectedIndex();
        
        Integer classNumber = Integer.parseInt(classString);

        Long teamId = teamRegisterService.registerTeam(new Team(
                name,
                acronym,
                Sport.SportsEnum.values()[sportEnumIndex],
                classDao.findByNumber(classNumber)
        ));
        
        assignTeamMembers(table, teamId);
    }
    
    private void assignTeamMembers(JTable table, Long teamId) {        
        HashMap teamMembersMap = new HashMap<Long, Boolean>();
        Team team = teamDao.findById(teamId).get();

        for (int i = 0; i < table.getRowCount(); i++){
            if (table.getValueAt(i, 2) != null && (Boolean) table.getValueAt(i, 2)){
                teamMembersMap.put(table.getValueAt(i, 0), table.getValueAt(i, 2));
            }
        }
        
        Iterator itr = teamMembersMap.entrySet().iterator();
                
        if (!itr.hasNext()) {
            JOptionPane.showMessageDialog(frame, "Marque ao menos um aluno");
            return;
        }
        
        while (itr.hasNext()){
            Map.Entry<Long, Boolean> newMap = (Map.Entry<Long, Boolean>) itr.next();
            
            StudentTeam st = new StudentTeam();
            EntityManager em = EntityManagerProvider.getEntityManager();
            Student student = em.find(Student.class, newMap.getKey());
            
            st.setStudentTeamKey(new StudentTeamKey(newMap.getKey(), teamId));
            st.setStudent(student);
            st.setTeam(team);
            
            if (teamRegisterService.registerStudentForTeam(st) == null) {
                JOptionPane.showMessageDialog(frame, "Erro no cadastro da relação entre aluno e time para aluno" + newMap.getValue());
                return;
            }
        }
        
        JOptionPane.showMessageDialog(frame, "Cadastrado com sucesso");
    }

    public void createCoach() {
        Coach coach = new Coach(this.frame.coachSportField.getText(),
                this.frame.coachNameField.getText(),
                this.frame.coachSurnameField1.getText(),
                parseStringToLocalDate(this.frame.coachBirthDateField.getText()),
                this.frame.coachGenderField.getText(),
                this.frame.coachPhoneField.getText(),
                this.frame.coachCPFField.getText());

        //Salva o treinador no banco de dados
        try {
            this.coachDao.create(coach);
            clearCoachRegisterFields();
            JOptionPane.showMessageDialog(null, "Sucesso!", "Treinador cadastrado com sucesso.", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro!", "Erro ao cadastrar treinador.", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void createTournament() {
        Tournament tournament = new Tournament(this.frame.tournamentNameField.getText(),
                parseStringToLocalDate(this.frame.tournamentStartDateField.getText()),
                parseStringToLocalDate(this.frame.tournamentEndDateField.getText()),
                this.frame.tournamentSportComboBox.getSelectedItem().toString());

        //Salva o torneio no banco de dados
        try {
            this.tournamentDao.create(tournament);
            JOptionPane.showMessageDialog(null, "Sucesso!", "Torneio cadastrado com sucesso.", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro!", "Erro ao cadastrar torneio.", JOptionPane.ERROR_MESSAGE);
        }
    }

    public LocalDate parseStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
            return localDate;
        } catch (Exception e) {
            return LocalDate.now();
        }
    }

    public void clearCoachRegisterFields() {
        this.frame.coachNameField.setText("");
        this.frame.coachSurnameField1.setText("");
        this.frame.coachGenderField.setText("");
        this.frame.coachPhoneField.setText("");
        this.frame.coachNameField.setText("");
        this.frame.coachCPFField.setText("");
        this.frame.coachBirthDateField.setText("");
        this.frame.coachSportField.setText("");
    }
    
    public void updateComboBoxes(){
        this.showClassesNumbers();
        this.showSportsNames();
    }
}

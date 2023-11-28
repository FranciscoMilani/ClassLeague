package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.application.Services.ClassService;
import br.ucs.classleague.application.Services.RegisterService;
import br.ucs.classleague.application.Services.TeamRegisterService;
import br.ucs.classleague.application.Services.TournamentService;
import br.ucs.classleague.domain.Coach;
import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.domain.Sport;
import br.ucs.classleague.domain.Sport.SportsEnum;
import br.ucs.classleague.domain.Student;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.domain.Tournament.TournamentPhase;
import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.data.CoachDao;
import br.ucs.classleague.infrastructure.data.DaoProvider;
import br.ucs.classleague.infrastructure.data.StudentDao;
import br.ucs.classleague.infrastructure.data.TeamDao;
import br.ucs.classleague.infrastructure.data.TournamentDao;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RegisterController {

    // View
    private GUI frame;
    
    // DAOs
    private ClassDao classDao;
    private StudentDao studentDao;
    private TeamDao teamDao;
    private CoachDao coachDao;
    private TournamentDao tournamentDao;
    
    // Services
    private RegisterService registerService;
    private ClassService classService;
    private TeamRegisterService teamRegisterService;
    private TournamentService tournamentService;

    public RegisterController(GUI frame) {
        this.frame = frame;
        
        classDao = DaoProvider.getClassDao();
        studentDao = DaoProvider.getStudentDao();
        teamDao = DaoProvider.getTeamDao();
        coachDao = DaoProvider.getCoachDao();
        tournamentDao = DaoProvider.getTournamentDao();
        
        classService = new ClassService();
        teamRegisterService = new TeamRegisterService();
        tournamentService = new TournamentService();
        registerService = new RegisterService();
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
    
    public void showCoachesNames() {
        frame.jTeamRegisterCoachPickerComboBox.removeAllItems();
        List<Object> coaches = List.of(getCoachesToArray());
        coaches.stream().forEach(c -> {
            frame.jTeamRegisterCoachPickerComboBox.addItem((Coach) c);
        });
    }

    public Boolean registerStudent() {
        Integer classNumber = Integer.parseInt(frame.jRegisterStudentClassComboBox.getSelectedItem().toString());
        SchoolClass schoolClass = classDao.findByNumber(classNumber);
        
        if (classNumber.equals(null)) {
            JOptionPane.showMessageDialog(
                    frame.jStudentRegisterPanel, 
                    "Sucesso!", 
                    "Aluno cadastrado com sucesso.", 
                    JOptionPane.INFORMATION_MESSAGE);
        }

        Student student = new Student(
                schoolClass,
                frame.jRegisterStudentFatherNameField.getText(),
                frame.jRegisterStudentMotherNameField.getText(),
                0,
                frame.jRegisterStudentNameField.getText(),
                frame.jRegisterStudentSurnameField.getText(),
                registerService.parseStringToLocalDate(frame.jRegisterStudentBirthdateField.getText()),
                frame.studentGenderComboBox.getSelectedItem().toString(),
                frame.jRegisterStudentTelephoneField.getText(),
                frame.jRegisterStudentCPFField.getText()
        );

        try {
            studentDao.create(student);
            clearStudentRegisterFields();
            JOptionPane.showMessageDialog(frame.jStudentRegisterPanel, 
                    "Sucesso!", 
                    "Aluno cadastrado com sucesso.", 
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame.jStudentRegisterPanel, 
            "Erro!", 
            "Erro ao cadastrar aluno.", 
            JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    public void registerClass() {
        String name = frame.jClassNameField.getText();
        String classNumber = frame.jClassNumber.getText();
        int shiftIndex = frame.jClassShift.getSelectedIndex();
        int cycleIndex = frame.jClassCycle.getSelectedIndex();

        Map<String, Object> response = registerService.validateClassFields(name, classNumber);
        if (!(boolean) response.get("isValid")) {
            String message = (String) response.get("message");
            JOptionPane.showMessageDialog(frame.classRegister, 
                message, 
                "Erro!",
                JOptionPane.ERROR_MESSAGE);

            return;
        }

        SchoolClass schoolClass = new SchoolClass(
                name,
                Integer.parseInt(classNumber),
                SchoolClass.SchoolShift.values()[shiftIndex],
                SchoolClass.EducationalCycle.values()[cycleIndex]
        );

        Map<String, Object> response2 = registerService.registerClass(schoolClass);
        String message = (String) response2.get("message");
        JOptionPane.showMessageDialog(frame.classRegister, message);

        clearClassRegisterFields();
    }

    public DefaultTableModel getTeamRegisterTableModel(int rowCount) {
        String[] columnHeaders = new String[]{"ID", "Nome", ""};

        DefaultTableModel teamRegisterTableModel = new DefaultTableModel(columnHeaders, rowCount) {

            // Apenas última coluna editável (checkbox)
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }

            // Cria colunas de tipos diferentes (última é Bool p/ checkbox)
            public Class<?> getColumnClass(int column) {
                if (column == 2) {
                    return Boolean.class;
                } else {
                    return String.class;
                }
            }
        };

        return teamRegisterTableModel;
    }

    public void updateTeamTableCells(String number) {
        List<Student> students;

        try {
            Integer n = Integer.parseInt(number);
            students = new ArrayList<>();
            students = classDao.getStudentsByClassNumber(n);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        DefaultTableModel model = (DefaultTableModel) frame.jTeamRegisterStudentsTable.getModel();
        ControllerUtilities.resetTable(frame.jTeamRegisterStudentsTable);
        
        for (int i = 0; i < students.size(); i++) {
            model.addRow(new Object[]{ 
                students.get(i).getId(), 
                students.get(i).getName(),
            });
        }
    }
    
    public DefaultTableModel getTournamentRegisterTableModel(int rowCount){
        String[] columnHeaders = new String[]{"ID", "Nome", ""};

        DefaultTableModel model = new DefaultTableModel(columnHeaders, rowCount) {

            // Apenas última coluna editável (checkbox)
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }

            // Cria colunas de tipos diferentes (última é Bool p/ checkbox)
            public Class<?> getColumnClass(int column) {
                if (column == 2) {
                    return Boolean.class;
                } else {
                    return String.class;
                }
            }
        };

        return model;
    }
    
    public void updateTournamentTeamTableCells(int sportEnumIndex){
        List<Team> teams;

        teams = teamDao.findBySportIndex(SportsEnum.values()[sportEnumIndex]);
        DefaultTableModel model = (DefaultTableModel) frame.tournamentRegisterTeamsTable.getModel();
        ControllerUtilities.resetTable(frame.tournamentRegisterTeamsTable);
        
        for (int i = 0; i < teams.size(); i++) {
            model.addRow(new Object[]{ 
                teams.get(i).getId(), 
                teams.get(i).getName(),
            });
        }
    }

    public String[] showSportsNames() {
        return teamRegisterService.getSportsNames();
    }
    
    public String[] showClassShiftNames() {
        return classService.getClassShiftNames();
    }
    
    public String[] showEducationalCycleNames() {
        return classService.getEducationalCycleNames();
    }
    
    public String[] showGender() {
        String[] str = new String[2];
        str[0] = "Feminino";
        str[1] = "Masculino";
        return str;
    }

    public void registerTeam(JTable table) {
        String name = frame.jTeamRegisterNameField.getText();
        String acronym = frame.jTeamRegisterAcronymField.getText();
        Coach coach = (Coach) frame.jTeamRegisterCoachPickerComboBox.getSelectedItem();
        Object schoolClass = frame.jTeamRegisterClassPickerComboBox.getSelectedItem();
        int sportEnumIndex = frame.jTeamRegisterSportComboBox.getSelectedIndex();
        
        var response = registerService.validateTeamFields(name, acronym, coach, schoolClass);
        if (!(boolean) response.get("isValid")) {
            JOptionPane.showMessageDialog(
                    frame.teamRegisterPanel, 
                    response.get("message")
            );
            
            return;
        }
        
        // Adiciona linha selecionadas
        List<Long> selectedMembersIds = new ArrayList<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            boolean isChecked = table.getValueAt(i, 2) != null 
                    && (boolean) table.getValueAt(i, 2);
            
            if (isChecked) {
                selectedMembersIds.add((Long) table.getValueAt(i, 0));
            }
        }
        
        if (selectedMembersIds.isEmpty()) {
            JOptionPane.showMessageDialog(frame.teamRegisterPanel, "Marque ao menos um aluno.");
            return;
        }
        
        // Cria time e estudantes
        SchoolClass entity = classDao.findByNumber(Integer.parseInt(schoolClass.toString()));
        Team team = new Team(name, acronym, SportsEnum.values()[sportEnumIndex], coach, entity);
        Map<String, Object> response2 = registerService.registerTeam(team, selectedMembersIds, sportEnumIndex, acronym);
        
        if (!(boolean) response2.get("isValid")) {
            JOptionPane.showMessageDialog(
                    frame.teamRegisterPanel, 
                    response2.get("message"),
                    "Erro!",
                    JOptionPane.ERROR_MESSAGE
            );
            
            return;
        } else {
            JOptionPane.showMessageDialog(
                    frame.teamRegisterPanel, 
                    response2.get("message"),
                    "Sucesso!",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            return;
        }
    }

    public void createCoach() {
        int sportEnumIndex = frame.coachSportComboBox.getSelectedIndex();
        Coach coach = new Coach(SportsEnum.values()[sportEnumIndex],
                this.frame.coachNameField.getText(),
                this.frame.coachSurnameField1.getText(),
                registerService.parseStringToLocalDate(this.frame.coachBirthDateField.getText()),
                this.frame.coachGenderComboBox.getSelectedItem().toString(),
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

    public void createTournament(JTable table) {
        int sportIndex = frame.tournamentSportComboBox.getSelectedIndex();
        String name = frame.tournamentNameField.getText();
        String startDate = frame.tournamentStartDateField.getText();
        String endDate = frame.tournamentEndDateField.getText();

        if (name.equals("")) {
            JOptionPane.showMessageDialog(
                    frame.tournamentRegisterPanel,
                    "Nome não pode ser nulo"
            );
            
            return;
        }
        
        List<Long> selectedTeamIds = getSelectedRowsToTeams(table);
        int phaseIndex = tournamentService.getHighestPhaseIndex(selectedTeamIds.size());
        Map<String, Object> response = registerService.verifyTournamentFields(selectedTeamIds.size(), phaseIndex);
        if (!(boolean) response.get("isValid")) {
            JOptionPane.showMessageDialog(frame.tournamentRegisterPanel, response.get("message"));
            return;
        }
        
        TournamentPhase startPhase = Tournament.TournamentPhase.values()[phaseIndex];
        Tournament tournament = new Tournament(
                frame.tournamentNameField.getText(),
                registerService.parseStringToLocalDate(startDate),
                registerService.parseStringToLocalDate(endDate),
                Sport.SportsEnum.values()[sportIndex],
                startPhase);
        
        Map<String, Object> response2 = registerService.registerTournament(selectedTeamIds, tournament);
        if (!(boolean) response2.get("isValid")) {
            JOptionPane.showMessageDialog( frame.tournamentRegisterPanel, response2.get("message"));
            return;
        }

        clearTournamentRegisterFields();
    }

    public DefaultTableModel getTableModel(int rowCount) {
        String[] columnHeaders = new String[]{"ID", "Nome", ""};

        DefaultTableModel teamRegisterTableModel = new DefaultTableModel(columnHeaders, rowCount) {

            // Apenas última coluna editável (checkbox)
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }

            // Cria colunas de tipos diferentes (última é Bool p/ checkbox)
            public Class<?> getColumnClass(int column) {
                if (column == 2) {
                    return Boolean.class;
                } else {
                    return String.class;
                }
            }
        };

        return teamRegisterTableModel;
    }

    private List<Long> getSelectedRowsToTeams(JTable table) {
        List<Long> teamIdList = new ArrayList<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 2) != null 
                    && (Boolean) table.getValueAt(i, 2)) {
                teamIdList.add((Long) table.getValueAt(i, 0));
            }
        }
        
        return teamIdList;
    }

    public void updateTournamentTableCells() {
        List<Team> teams = new ArrayList<>();
        try {
            teams = teamDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        DefaultTableModel model = getTableModel(teams.size());
        frame.tournamentRegisterTeamsTable.setModel(model);

        for (int i = 0; i < teams.size(); i++) {
            System.out.println(teams.get(i).toString());
            model.setValueAt(teams.get(i).getId(), i, 0);
            model.setValueAt(teams.get(i).getName(), i, 1);
        }
    }

    public void clearCoachRegisterFields() {
        this.frame.coachNameField.setText("");
        this.frame.coachSurnameField1.setText("");
        this.frame.coachPhoneField.setText("");
        this.frame.coachNameField.setText("");
        this.frame.coachCPFField.setText("");
        this.frame.coachBirthDateField.setText("");
    }

    public void clearStudentRegisterFields() {
        this.frame.jRegisterStudentNameField.setText("");
        this.frame.jRegisterStudentSurnameField.setText("");
        this.frame.jRegisterStudentBirthdateField.setText("");
        this.frame.jRegisterStudentCPFField.setText("");
        this.frame.jRegisterStudentFatherNameField.setText("");
        this.frame.jRegisterStudentMotherNameField.setText("");
        this.frame.jRegisterStudentTelephoneField.setText("");
        //this.frame.jRegisterStudentClassComboBox.setSelectedIndex(-1);
    }
    
    public void clearTournamentRegisterFields() {
        ControllerUtilities.resetTable(frame.tournamentRegisterTeamsTable);
        frame.tournamentNameField.setText("");
        frame.tournamentStartDateField.setText("");
        frame.tournamentEndDateField.setText("");
        frame.tournamentSportComboBox.setSelectedIndex(0);
    }
    
    public void clearClassRegisterFields() {
        frame.jClassNameField.setText("");
        frame.jClassNumber.setText("");
        frame.jClassShift.setSelectedIndex(0);
        frame.jClassCycle.setSelectedIndex(0);
    }

    public void updateComboBoxes() {
        this.showClassesNumbers();
        this.showSportsNames();
        this.showCoachesNames();
    }
    
    public Object[] getCoachesToArray() {
        int sportIndex = frame.jTeamRegisterSportComboBox.getSelectedIndex();
        Object[] coachesToArray = teamRegisterService.getCoachesToArray(sportIndex);
        
        if (coachesToArray == null) {
            System.err.println("Coaches retornado vazio");
            return new Object[0];
        }
        
        return coachesToArray;    
    }
}

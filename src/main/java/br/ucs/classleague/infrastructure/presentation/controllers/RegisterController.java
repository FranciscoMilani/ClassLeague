package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.application.Services.ClassService;
import br.ucs.classleague.application.Services.TeamRegisterService;
import br.ucs.classleague.application.Services.TournamentService;
import br.ucs.classleague.domain.Coach;
import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.domain.Sport;
import br.ucs.classleague.domain.Sport.SportsEnum;
import br.ucs.classleague.domain.Student;
import br.ucs.classleague.domain.StudentTeam;
import br.ucs.classleague.domain.StudentTeamKey;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.domain.Tournament.TournamentPhase;
import br.ucs.classleague.domain.TournamentTeam;
import br.ucs.classleague.domain.TournamentTeamKey;
import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.data.CoachDao;
import br.ucs.classleague.infrastructure.data.DaoProvider;
import br.ucs.classleague.infrastructure.data.StudentDao;
import br.ucs.classleague.infrastructure.data.TeamDao;
import br.ucs.classleague.infrastructure.data.TournamentDao;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        int classNumber = Integer.parseInt(frame.jRegisterStudentClassComboBox.getSelectedItem().toString());
        SchoolClass schoolClass = classDao.findByNumber(classNumber);

        Student student = new Student(
                schoolClass,
                frame.jRegisterStudentFatherNameField.getText(),
                frame.jRegisterStudentMotherNameField.getText(),
                0,
                frame.jRegisterStudentNameField.getText(),
                frame.jRegisterStudentSurnameField.getText(),
                parseStringToLocalDate(frame.jRegisterStudentBirthdateField.getText()),
                frame.studentGenderComboBox.getSelectedItem().toString(),
                frame.jRegisterStudentTelephoneField.getText(),
                frame.jRegisterStudentCPFField.getText()
        );

        try {
            studentDao.create(student);
            clearStudentRegisterFields();
            JOptionPane.showMessageDialog(null, "Sucesso!", "Aluno cadastrado com sucesso.", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro!", "Erro ao cadastrar aluno.", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    public Boolean registerClass() {
        try {
            int shiftIndex = frame.jClassShift.getSelectedIndex();
            int cycleIndex = frame.jClassCycle.getSelectedIndex();
            int number = Integer.parseInt(frame.jClassNumber.getText());
            
            boolean valid = classService.checkForValidNumber(number);
            if (!valid) {
                JOptionPane.showMessageDialog(frame.classRegister,  "Número da turma já existe, insira outro.","Erro!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            SchoolClass schoolClass = new SchoolClass(
                    frame.jClassNameField.getText(),
                    number,
                    SchoolClass.SchoolShift.values()[shiftIndex],
                    SchoolClass.EducationalCycle.values()[cycleIndex]
            );

            classService.registerClass(schoolClass);
            JOptionPane.showMessageDialog(null, "Sucesso!", "Turma cadastrada com sucesso.", JOptionPane.INFORMATION_MESSAGE);
            clearClassRegisterFields();
            return true; 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro!", "Erro ao cadastrar turma.", JOptionPane.ERROR_MESSAGE);
            clearClassRegisterFields();
            return false;
        }
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
        String classString = frame.jTeamRegisterClassPickerComboBox.getSelectedItem().toString();
        int sportEnumIndex = frame.jTeamRegisterSportComboBox.getSelectedIndex();

        Integer classNumber = Integer.parseInt(classString);

        Long teamId = teamRegisterService.registerTeam(new Team(
                name,
                acronym,
                SportsEnum.values()[sportEnumIndex],
                coach,
                classDao.findByNumber(classNumber)
        ));

        assignTeamMembers(table, teamId);
    }

    private void assignTeamMembers(JTable table, Long teamId) {
        HashMap teamMembersMap = new HashMap<Long, Boolean>();
        Team team = teamDao.findById(teamId).get();

        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 2) != null && (Boolean) table.getValueAt(i, 2)) {
                teamMembersMap.put(table.getValueAt(i, 0), table.getValueAt(i, 2));
            }
        }

        Iterator itr = teamMembersMap.entrySet().iterator();

        if (!itr.hasNext()) {
            JOptionPane.showMessageDialog(frame, "Marque ao menos um aluno");
            return;
        }

        while (itr.hasNext()) {
            Map.Entry<Long, Boolean> newMap = (Map.Entry<Long, Boolean>) itr.next();

            StudentTeam st = new StudentTeam();
            Student student = studentDao.findById(newMap.getKey()).get();

            st.setStudentTeamKey(new StudentTeamKey(newMap.getKey(), teamId));
            st.setStudent(student);
            st.setTeam(team);

            if (teamRegisterService.registerStudentForTeam(st) == null) {
                JOptionPane.showMessageDialog(frame, "Erro no cadastro da relação entre aluno e time para aluno.");
                return;
            }
        }

        JOptionPane.showMessageDialog(frame, "Cadastrado com sucesso");
    }

    public void createCoach() {
        int sportEnumIndex = frame.coachSportComboBox.getSelectedIndex();
        Coach coach = new Coach(SportsEnum.values()[sportEnumIndex],
                this.frame.coachNameField.getText(),
                this.frame.coachSurnameField1.getText(),
                parseStringToLocalDate(this.frame.coachBirthDateField.getText()),
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
        Integer count = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 2) != null && (Boolean) table.getValueAt(i, 2)) {
                count++;
            }
        }
        System.out.println(count);
        if (!isPowerOfTwo(count) || count < 4) {
            JOptionPane.showMessageDialog(null, "Quantidade de times incorreta para realização do chaveamento. Selecione potências de 2 maiores ou igual a 4.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (count > 16) {
            JOptionPane.showMessageDialog(null, "Quantidade de times excedida, limite: 16 times.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int sportEnumIndex = frame.jTeamRegisterSportComboBox.getSelectedIndex();
        int phaseIndex = tournamentService.getHighestPhaseIndex(count);
        TournamentPhase startPhase = TournamentPhase.values()[phaseIndex];
        
        Tournament tournament = new Tournament(this.frame.tournamentNameField.getText(),
                parseStringToLocalDate(this.frame.tournamentStartDateField.getText()),
                parseStringToLocalDate(this.frame.tournamentEndDateField.getText()),
                Sport.SportsEnum.values()[sportEnumIndex],
                startPhase);

        //Salva o torneio no banco de dados
        try {
            Long tournamentId = this.tournamentDao.create(tournament).getId();
            assignTeamsToTournament(table, tournamentId);
            tournamentService.createInitialSimpleClashes(tournament);
            JOptionPane.showMessageDialog(null, "Sucesso!", "Torneio cadastrado com sucesso.", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro!", "Erro ao cadastrar torneio.", JOptionPane.ERROR_MESSAGE);
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

    private void assignTeamsToTournament(JTable table, Long tournamentId) {
        Tournament tournament = tournamentDao.findById(tournamentId).get();
        Set<TournamentTeam> ttSet = new HashSet<>();
        
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 2) != null && (Boolean) table.getValueAt(i, 2)) {
                Long teamId = (Long) table.getValueAt(i, 0);
                Team team = teamDao.findById(teamId).get();
                
                // Cria chave composta para criar a entidade de junção Tournament-Team
                TournamentTeam tt = new TournamentTeam();
                tt.setTournamentTeamKey(new TournamentTeamKey(tournamentId, teamId));
                tt.setTournament(tournament);
                tt.setTeam(team);

                ttSet.add(tt);
            }
        }
        
        // Atribui ao torneio o conjunto das entidades de junção criadas
        tournament.setTournamentTeam(ttSet);
        
        // Edita entidade no banco e faz refresh da entidade p/ atualizar seu estado com a informação do banco
        // Sem refresh(), não atualiza a referência aos TournamentTeams. Talvez a razão seja porque não foi feito persist dos TournamentTeams direto na criação.
        tournamentDao.update(tournament);
        tournamentDao.refresh(tournament);
    }

    public static boolean isPowerOfTwo(int number) {
        if (number <= 0) {
            return false;
        }
        // Verifica se o número é uma potência de 2.
        return (number & (number - 1)) == 0 && (number > 1);
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

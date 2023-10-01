package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.application.Services.ClassService;
import br.ucs.classleague.domain.Coach;
import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.domain.Student;
import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.data.CoachDao;
import br.ucs.classleague.infrastructure.data.StudentDao;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    private ClassService classService = new ClassService();
    private CoachDao coachDao = new CoachDao();

    public RegisterController(GUI frame) {
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

    public Boolean registerStudent() {

        //Cria um objeto da classe schoolclass para informar no cadastro do aluno
        SchoolClass schoolClass = new SchoolClass(
                "",
                0,
                null,
                null
        );

        Student student = new Student(
                null,
                frame.jRegisterStudentFatherNameField.getText(),
                frame.jRegisterStudentMotherNameField.getText(),
                0,
                frame.jRegisterStudentNameField.getText(),
                "",
                null,
                frame.jRegisterStudentGenderField.getText(),
                //                "",
                "",
                null
        );

        studentDao.create(student);

        return true;
    }

    public Boolean registerClass() {
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

    private void resetTeamTable() {
        JTable table = frame.jTeamRegisterStudentsTable;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        table.revalidate();
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
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        DefaultTableModel model = getTableModel(students.size());

        frame.jTeamRegisterStudentsTable.setModel(model);

        for (int i = 0; i < students.size(); i++) {
            System.out.println(students.get(i).toString());
            model.setValueAt(students.get(i).getId(), i, 0);
            model.setValueAt(students.get(i).getName(), i, 1);
        }
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
}

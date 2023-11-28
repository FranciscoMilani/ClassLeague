package br.ucs.classleague.presentation.controllers;

import br.ucs.classleague.domain.Coach;
import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.domain.Student;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.data.CoachDao;
import br.ucs.classleague.infrastructure.data.StudentDao;
import br.ucs.classleague.infrastructure.data.TeamDao;
import br.ucs.classleague.presentation.views.GUI;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class visualizationDataController {

    private GUI frame;
    private ClassDao classDao = new ClassDao();
    private StudentDao studentDao = new StudentDao();
    private TeamDao teamDao = new TeamDao();
    private CoachDao coachDao = new CoachDao();

    public visualizationDataController(GUI frame) {
        this.frame = frame;
    }

    public DefaultTableModel getClassesTableModel(int rowCount) {
        String[] columnHeaders = new String[]{"ID", "Nome", "Número", "Turno", "Ciclo Escolar"};

        DefaultTableModel classesTableModel = new DefaultTableModel(columnHeaders, rowCount) {

            public Class<?> getColumnClass(int column) {
                return String.class;
            }
        };

        return classesTableModel;
    }

    public void updateClassVisualizationCells() {
        List<SchoolClass> classes = new ArrayList<>();
        try {
            classes = this.classDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        DefaultTableModel model = getClassesTableModel(classes.size());
        frame.jClassesTable.setModel(model);

        for (int i = 0; i < classes.size(); i++) {
            model.setValueAt(classes.get(i).getId(), i, 0);
            model.setValueAt(classes.get(i).getName(), i, 1);
            model.setValueAt(classes.get(i).getNumber(), i, 2);
            model.setValueAt(classes.get(i).getSchoolShift().getName(), i, 3);
            model.setValueAt(classes.get(i).getEducationalCycle().getName(), i, 4);
        }
    }

    public DefaultTableModel getStudentsTableModel(int rowCount) {
        String[] columnHeaders = new String[]{"ID", "Nome", "Sobrenome", "CPF", "Nascimento", "Gênero", "Telefone", "Nome Pai", "Nome Mãe", "Turma", "Pontos"};

        DefaultTableModel classesTableModel = new DefaultTableModel(columnHeaders, rowCount) {

            public Class<?> getColumnStudent(int column) {
                return String.class;
            }
        };

        return classesTableModel;
    }

    public void updateStudentVisualizationCells() {
        List<Student> students = new ArrayList<>();
        try {
            students = this.studentDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        DefaultTableModel model = getStudentsTableModel(students.size());
        frame.jStudentsTable.setModel(model);

        for (int i = 0; i < students.size(); i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String birthDate = formatter.format(students.get(i).getBirthDate());
            model.setValueAt(students.get(i).getId(), i, 0);
            model.setValueAt(students.get(i).getName(), i, 1);
            model.setValueAt(students.get(i).getSurname(), i, 2);
            model.setValueAt(students.get(i).getCpf(), i, 3);
            model.setValueAt(birthDate, i, 4);
            model.setValueAt(students.get(i).getGender(), i, 5);
            model.setValueAt(students.get(i).getTelephone(), i, 6);
            model.setValueAt(students.get(i).getFatherName(), i, 7);
            model.setValueAt(students.get(i).getMotherName(), i, 8);
            model.setValueAt(students.get(i).getSchoolClass().getName(), i, 9);
            model.setValueAt(students.get(i).getPontos(), i, 10);
        }
    }

    public DefaultTableModel getTeamsTableModel(int rowCount) {
        String[] columnHeaders = new String[]{"ID", "Nome", "Acrônimo", "Esporte", "Turma"};

        DefaultTableModel teamsTableModel = new DefaultTableModel(columnHeaders, rowCount) {

            public Class<?> getColumnTeams(int column) {
                return String.class;
            }
        };

        return teamsTableModel;
    }

    public void updateTeamVisualizationCells() {
        List<Team> teams = new ArrayList<>();
        try {
            teams = this.teamDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        DefaultTableModel model = getTeamsTableModel(teams.size());
        frame.jTeamsTable.setModel(model);

        for (int i = 0; i < teams.size(); i++) {
            model.setValueAt(teams.get(i).getId(), i, 0);
            model.setValueAt(teams.get(i).getName(), i, 1);
            model.setValueAt(teams.get(i).getAcronym(), i, 2);
            model.setValueAt(teams.get(i).getSport().getName(), i, 3);
            model.setValueAt(teams.get(i).getSchoolClass().getName(), i, 4);
        }
    }

    public DefaultTableModel getCoachTableModel(int rowCount) {
        String[] columnHeaders = new String[]{"ID", "Nome", "Sobrenome", "CPF", "Data Nascimento", "Gênero", "Telefone", "Esporte"};

        DefaultTableModel coachTableModel = new DefaultTableModel(columnHeaders, rowCount) {

            public Class<?> getColumnCoach(int column) {
                return String.class;
            }
        };

        return coachTableModel;
    }

    public void updateCoachVisualizationCells() {
        List<Coach> coach = new ArrayList<>();
        try {
            coach = this.coachDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        DefaultTableModel model = getCoachTableModel(coach.size());
        frame.jCoachTable.setModel(model);

        for (int i = 0; i < coach.size(); i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String birthDate = formatter.format(coach.get(i).getBirthDate());
            model.setValueAt(coach.get(i).getId(), i, 0);
            model.setValueAt(coach.get(i).getName(), i, 1);
            model.setValueAt(coach.get(i).getSurname(), i, 2);
            model.setValueAt(coach.get(i).getCpf(), i, 3);
            model.setValueAt(birthDate, i, 4);
            model.setValueAt(coach.get(i).getGender(), i, 5);
            model.setValueAt(coach.get(i).getTelephone(), i, 6);
            model.setValueAt(coach.get(i).getSport(), i, 7);
        }
    }

    public GUI getFrame() {
        return frame;
    }

    public void setFrame(GUI frame) {
        this.frame = frame;
    }
}

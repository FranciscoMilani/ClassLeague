package br.ucs.classleague.presentation.controllers;

import br.ucs.classleague.application.services.TournamentService;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.domain.TournamentTeam;
import br.ucs.classleague.infrastructure.data.DaoProvider;
import br.ucs.classleague.infrastructure.data.StudentDao;
import br.ucs.classleague.infrastructure.data.StudentMatchDao;
import br.ucs.classleague.infrastructure.data.TeamDao;
import br.ucs.classleague.infrastructure.data.TournamentTeamDao;
import br.ucs.classleague.presentation.model.TournamentModel;
import br.ucs.classleague.presentation.views.GUI;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TournamentDataController {
    
    private GUI view;
    private TournamentModel tournamentModel;
    private TeamDao teamDao;
    private TournamentTeamDao ttDao;
    private StudentDao studentDao;
    private StudentMatchDao studentMatchDao;
    private TournamentService tournamentService;
    
    public TournamentDataController(GUI view, TournamentModel tournamentModel) {
        this.view = view;
        this.tournamentModel = tournamentModel;
        this.ttDao = DaoProvider.getTournamentTeamDao();
        this.studentDao = DaoProvider.getStudentDao();
        this.studentMatchDao = DaoProvider.getStudentMatchDao();
        this.tournamentService = new TournamentService();
    }
    
    public void setupTournamentParticipants() {
        fillTournamentParticipantsList();
    }
    
    public void setupTournamentRankings() {
        showTournamentRankingsCB();
        fillTournamentRankingsList();
    }
    
    public void updateTournamentRankingsList() {
        fillTournamentRankingsList();
    }
        
    private Object[] getTeamsToArray() {
        Tournament tournament = tournamentModel.getTournament();
        Object[] teamsToArray = tournamentService.getTeamsToArray(tournament.getId());
        
        if (teamsToArray == null) {
            System.err.println("Teams retornado vazio");
            return new Object[0];
        }
        
        return teamsToArray;
    }
    
    private void fillTournamentParticipantsList() {
        Tournament tournament = tournamentModel.getTournament();
        ControllerUtilities.resetTable(view.tournamentParticipantsTable);
        DefaultTableModel model = (DefaultTableModel) view.tournamentParticipantsTable.getModel();
        List<TournamentTeam> ttList = ttDao.findByTournamentId(tournament.getId());
        
        for (int i = 0; i < ttList.size(); i++) {
            TournamentTeam tt = ttList.get(i);
            Team team = ttList.get(i).getTeam();
            
            model.addRow(new Object[]{ 
                team.getAcronym(),
                team.getName(),
                team.getSchoolClass().getName(),
                team.getCoach().getName(),
                tt.getPoints() == null ? "" : tt.getPoints()
            });
        }
    }
    
    private void fillTournamentRankingsList() {
        Tournament tournament = tournamentModel.getTournament();
        ControllerUtilities.resetTable(view.rankingTable);
        DefaultTableModel model = (DefaultTableModel) view.rankingTable.getModel();
        
        if (view.tournamentRankingTeamsComboBox.getSelectedIndex() != -1) {
            Team team = (Team) view.tournamentRankingTeamsComboBox.getSelectedItem();
            Long teamId = team.getId() != -1L ? team.getId() : null;
            List<Object[]> result = studentMatchDao.findAllByTournamentTeamId(
                    tournament.getId(),
                    teamId
            );

            for (int i = 0; i < result.size(); i++) {
                model.addRow(result.get(i));
            }
        }
    }
    
    private void showTournamentRankingsCB() {
        view.tournamentRankingTeamsComboBox.removeAllItems();
        List<Object> teams = List.of(getTeamsToArray());
        
        Team defaultTeam = new Team();
        defaultTeam.setId(-1L);
        defaultTeam.setName("━━ SELECIONAR ━━");
        
        view.tournamentRankingTeamsComboBox.addItem(defaultTeam);
        teams.forEach(t -> {
            view.tournamentRankingTeamsComboBox.addItem((Team) t);
        });
    }
}

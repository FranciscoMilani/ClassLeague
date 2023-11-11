package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.application.Services.TournamentService;
import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.domain.Tournament.TournamentPhase;
import br.ucs.classleague.domain.Tournament.TournamentStatus;
import br.ucs.classleague.infrastructure.data.DaoProvider;
import br.ucs.classleague.infrastructure.data.MatchDao;
import br.ucs.classleague.infrastructure.data.TournamentDao;
import br.ucs.classleague.infrastructure.presentation.model.MatchModel;
import br.ucs.classleague.infrastructure.presentation.model.TournamentModel;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

public class TournamentController {
    
    public static Long selectedMatchId = -1L;
    
    private GUI frame;
    private TournamentModel tournamentModel;
    private MatchModel matchModel;
    private TournamentDao tournamentDao;
    private MatchDao matchDao;
    private TournamentService tournamentService;
    
    public TournamentController(GUI frame, TournamentModel tournamentModel, MatchModel matchModel) {
        this.frame = frame;
        this.tournamentModel = tournamentModel;
        this.matchModel = matchModel;
        matchDao = DaoProvider.getMatchDao();
        tournamentDao = DaoProvider.getTournamentDao();
        tournamentService = new TournamentService();
    }
    
    public void showTournamentDialog(String tournamentId){
        Long curTournamentId = Long.parseLong(tournamentId);
        
        frame.jTournamentSelectTable.getSelectionModel().clearSelection();
        Tournament tournament = tournamentDao.findById(curTournamentId).get();
        
        if (tournament != null) {
            tournamentModel.setTournament(tournament);
            fillTournamentData();
            frame.tournamentDialog.setVisible(true);
        }
    }
    
    public void checkEnableMatch(String matchId) {
        Long id = Long.parseLong(matchId);
        Match match = matchDao.findById(id).get();
        
        matchModel.setMatchId(id);
        tournamentModel.checkEnableMatch(match);
    }
    
    public void fillTournamentData() {
        Tournament tournament = tournamentModel.getTournament();
        
        boolean isEnded = tournamentService.checkEndedTournament(tournament);
        frame.tournamentStatusLabel.setText(TournamentStatus
                .mapBoolToStatus(isEnded)
                .toUpperCase()
        );

        frame.tournamentDialogNameData.setText(tournament.getName());
        frame.tournamentDialogSportTypeInfoData.setText(tournament.getSportEnum().getName());
        frame.tournamentDialogStartDateInfoData.setText(tournament.getStartTime().toString());
        frame.tournamentDialogEndInfoData.setText(tournament.getEndTime().toString());
        frame.tournamentDialogPhaseInfoData.setText(tournament.getPhase().getName());
        frame.tournamentHistoryComboBox.setEnabled(true);
        
        fillTournamentMatchTableData();
        fillPreviousMatchesComboBox();
        fillPreviousMatchesTableData();

        boolean canStart = tournamentService.checkNextPhase(tournament);
        frame.startNewPhaseButton.setEnabled(canStart);
    }
    
    public DefaultTableModel getFullTableModel(){
        ControllerUtilities.resetTable(frame.jTournamentSelectTable);
        
        try {
            List<Tournament> tournaments = tournamentDao.findAllOrderByStartDate(); //tournamentDao.findAll();
            return fillTournamentListTableData(tournaments);
        } catch (Exception e){  
            e.printStackTrace();
            return getTournamentListTableModel(0);
        }
    }
    
    public DefaultTableModel getQueriedTableModel(String name) {
        ControllerUtilities.resetTable(frame.jTournamentSelectTable);
        name = name.trim();
        
        try {
            List<Tournament> tournaments = tournamentDao.findByName(name);
            return fillTournamentListTableData(tournaments);
        } catch (Exception e){  
            e.printStackTrace();
            return getTournamentListTableModel(0);
        }
    }
        
    public DefaultTableModel getTournamentListTableModel(int rowCount) {
        String[] columnHeaders = new String[] {"ID", "Esporte", "Nome", "Data de início"};

        DefaultTableModel tournamentListModel = new DefaultTableModel(columnHeaders, rowCount) {           
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        return tournamentListModel;
    }
    
    private DefaultTableModel fillTournamentListTableData(List<Tournament> tournaments) {
        DefaultTableModel model = (DefaultTableModel) frame.jTournamentSelectTable.getModel();
                            
        for (int i = 0; i < tournaments.size(); i++) {
            model.addRow(new Object[]{ 
                tournaments.get(i).getId(), 
                tournaments.get(i).getSportEnum().getName() ,
                tournaments.get(i).getName(),
                tournaments.get(i).getStartTime()
            });
        }
        
        return model;
    }
    
    public DefaultTableModel getTournamentMatchTableModel(int rowCount){
        String[] columnHeaders = new String[] {"ID", "Time 1", "Time 2", "Vencedor", "Placar"};

        DefaultTableModel tournamentMatchModel = new DefaultTableModel(columnHeaders, rowCount) {           
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        return tournamentMatchModel;
    }
    
    public void fillTournamentMatchTableData() {
        frame.tournamentMatchesTable.clearSelection();
        Tournament tournament = tournamentModel.getTournament();
        DefaultTableModel model = (DefaultTableModel) frame.tournamentMatchesTable.getModel();
        
        // coleta apenas confrontos da fase atual do torneio
        List<Match> matches = tournament.getMatches()
                .stream()
                .filter((m) -> m.getPhase() == tournament.getPhase())
                .collect(Collectors.toList());
  
        ControllerUtilities.resetTable(frame.tournamentMatchesTable);
        
        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            
            String col5 = String.format("%d - %d",
                    match.getFirst_team_score(),
                    match.getSecond_team_score()
            );

            model.addRow(new Object[]{
                match.getId(), 
                match.getFirst_team().getName(),
                match.getSecond_team().getName(),
                match.getWinner() == null ? "" : match.getWinner().getName(),
                match.getEnded() ? col5 : ""
            });
        }
    }
    
    public DefaultTableModel getPreviousMatchesTableModel(int rowCount){
        String[] columnHeaders = new String[] {"Times", "Vencedor", "Placar"};

        DefaultTableModel tournamentMatchModel = new DefaultTableModel(columnHeaders, rowCount) {           
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        return tournamentMatchModel;
    }
    
    public void fillPreviousMatchesTableData() {
        Tournament tournament = tournamentModel.getTournament();
        DefaultTableModel model = (DefaultTableModel) frame.tournamentPreviousMatchesTable.getModel();
        TournamentPhase tPhase;
        
        if ((String) frame.tournamentHistoryComboBox.getSelectedItem() != null) {
            frame.tournamentHistoryComboBox.setEnabled(true);
            tPhase = TournamentPhase.fromString((String) frame.tournamentHistoryComboBox.getSelectedItem());
        } else {
            System.out.println("nulo");
            frame.tournamentHistoryComboBox.setEnabled(false);
            tPhase = null;
        }
        
        List<Match> matches = tournament.getMatches()
                .stream()
                .filter((m) -> m.getPhase() == tPhase)
                .collect(Collectors.toList());
  
        ControllerUtilities.resetTable(frame.tournamentPreviousMatchesTable); 

        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            
            String col1 = String.format("%s x %s",
                    match.getFirst_team().getName(),
                    match.getSecond_team().getName()
            );  
            
            String col2 = match.getWinner().getName();
            
            String col3 = String.format("%d - %d",
                    match.getFirst_team_score(),
                    match.getSecond_team_score()
            );
            
            model.addRow(new Object[]{ col1, col2, col3 });
        }
    }
    
    public void fillPreviousMatchesComboBox() {
        frame.tournamentHistoryComboBox.removeAllItems();
        
        String[] phases = tournamentService.getPhasesInTournament(tournamentModel.getTournament());
        for (String p : phases) {
            frame.tournamentHistoryComboBox.addItem(p);
        }
    }
    
    public void resetTournamentWindow() {
        tournamentModel.setTournament(null);
        tournamentModel.setEnableMatch(false);
    }
    
    public void startNextPhase() {
        tournamentService.startNextPhase(tournamentModel.getTournament());
        fillTournamentMatchTableData();
        fillPreviousMatchesComboBox();
        fillPreviousMatchesTableData();
        frame.startNewPhaseButton.setEnabled(false);
    }
}

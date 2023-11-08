package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.application.Services.TournamentService;
import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.infrastructure.data.DaoFactory;
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
        matchDao = DaoFactory.getMatchDao();
        tournamentDao = DaoFactory.getTournamentDao();
        tournamentService = new TournamentService();
    }
    
    public void showTournamentDialog(String tournamentId){
        Long curTournamentId = Long.parseLong(tournamentId);
        frame.jTournamentSelectTable.getSelectionModel().clearSelection();
        
        Tournament t = tournamentDao.findById(curTournamentId).get();
        if (t != null) {
            tournamentModel.setTournament(t);
        }
        
        fillTournamentMatchTableData(tournamentModel.getTournament());
        frame.tournamentDialog.setVisible(true);
    }
    
    public void checkEnableMatch(String matchId) {
        Long id = Long.parseLong(matchId);
        Match match = matchDao.findById(id).get();
        
        matchModel.setMatchId(id);
        tournamentModel.checkEnableMatch(match);
    }
    
    public void fillTournamentData() {
        Tournament t = tournamentModel.getTournament();

        frame.tournamentDialogNameData.setText(t.getName());
        frame.tournamentDialogSportTypeInfoData.setText(t.getSportEnum().getName());
        frame.tournamentDialogStartDateInfoData.setText(t.getStartTime().toString());
        frame.tournamentDialogEndInfoData.setText(t.getEndTime().toString());
        frame.tournamentDialogPhaseLabel.setText(t.getPhase().getName());

        boolean canStart = tournamentService.checkNextPhase(t);
        frame.startNewPhaseButton.setEnabled(canStart);
    }
    
    public DefaultTableModel getFullTableModel(){
        ControllerUtilities.resetTable(frame.jTournamentSelectTable);
        
        try {
            List<Tournament> tournaments = tournamentDao.findAll();
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
        String[] columnHeaders = new String[] {"ID", "Time 1", "Time 2"};

        DefaultTableModel tournamentMatchModel = new DefaultTableModel(columnHeaders, rowCount) {           
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        return tournamentMatchModel;
    }
    
    public void fillTournamentMatchTableData(Tournament tournament) {
        DefaultTableModel model = (DefaultTableModel) frame.tournamentMatchesTable.getModel();
        
        // coleta apenas confrontos da fase atual do torneio
        List<Match> matches = tournament.getMatches()
                .stream()
                .filter((m) -> m.getPhase() == tournament.getPhase())
                .collect(Collectors.toList());
  
        ControllerUtilities.resetTable(frame.tournamentMatchesTable);
        
        for (int i = 0; i < matches.size(); i++) {;
            model.addRow(new Object[]{
                matches.get(i).getId(), 
                matches.get(i).getFirst_team().getName(),
                matches.get(i).getSecond_team().getName(),
            });
        }
    }
    
    public void resetTournamentWindow() {
        tournamentModel.setTournament(null);
        tournamentModel.setEnableMatch(false);
    }
    
    public void startNextPhase() {
        tournamentService.startNextPhase(tournamentModel.getTournament());
        fillTournamentMatchTableData(tournamentModel.getTournament());
        frame.startNewPhaseButton.setEnabled(false);
    }
}

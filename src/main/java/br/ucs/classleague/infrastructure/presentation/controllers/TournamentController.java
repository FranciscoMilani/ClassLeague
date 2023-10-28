package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.infrastructure.data.DaoFactory;
import br.ucs.classleague.infrastructure.data.MatchDao;
import br.ucs.classleague.infrastructure.data.TournamentDao;
import br.ucs.classleague.infrastructure.presentation.model.TournamentModel;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TournamentController {
    
    public static Long curTournamentId = -1L;
    public static Long selectedMatchId = -1L;
    
    private GUI frame;
    private TournamentModel tournamentModel;
    
    private TournamentDao tournamentDao = DaoFactory.getTournamentDao();
    private MatchDao matchDao = DaoFactory.getMatchDao();
    
    public TournamentController(GUI frame, TournamentModel tournamentModel) {
        this.frame = frame;
        this.tournamentModel = tournamentModel;
    }
    
    public void showTournamentDialog(String tournamentId){
        // TODO: mover o currentTournamentId para o model
        curTournamentId = Long.parseLong(tournamentId);
        frame.jTournamentSelectTable.getSelectionModel().clearSelection();
        frame.tournamentDialog.setVisible(true);
        
        fillTournamentMatchTableData(curTournamentId);
        tournamentModel.setTournamentId(curTournamentId);
    }
    
    public void checkEnableMatch(String matchId){
        Long mId = Long.parseLong(matchId);
        Match match = matchDao.findById(mId).get();
        tournamentModel.checkEnableMatch(match);
    }
    
    public void fillTournamentData() {
        if (curTournamentId != -1){
            Tournament t = tournamentDao.findById(curTournamentId).get();
            
            frame.tournamentDialogNameData.setText(t.getName());
            frame.tournamentDialogSportTypeInfoData.setText(t.getSportType().getName());
            frame.tournamentDialogStartDateInfoData.setText(t.getStartTime().toString());
            frame.tournamentDialogEndInfoData.setText(t.getEndTime().toString());
            //frame.tournamentDialogPhaseLabel.setText(t.getPhase().getName());
        }
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
                tournaments.get(i).getSportType().getName() ,
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
    
    public void fillTournamentMatchTableData(Long tournamentId) {
        DefaultTableModel model = (DefaultTableModel) frame.tournamentMatchesTable.getModel();
        Tournament tournament = tournamentDao.findById(curTournamentId).get();
        List<Match> matches = new ArrayList<>();
        matches.addAll(tournament.getMatches());
                           
        ControllerUtilities.resetTable(frame.tournamentMatchesTable);
        for (int i = 0; i < matches.size(); i++) {
            model.addRow(new Object[]{
                matches.get(i).getId(), 
                matches.get(i).getFirst_team().getName(),
                matches.get(i).getSecond_team().getName(),
            });
        }
    }
    
    public void resetTournamentWindow() {
        tournamentModel.setTournamentId(-1L);
        tournamentModel.setEnableMatch(false);
    }
}

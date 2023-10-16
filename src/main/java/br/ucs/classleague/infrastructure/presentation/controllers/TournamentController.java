package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.infrastructure.data.DaoFactory;
import br.ucs.classleague.infrastructure.data.TournamentDao;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TournamentController {
    
    private GUI frame;
    private TournamentDao tournamentDao = DaoFactory.getTournamentDao();
    public static Long curTournamentId = -1L;
    
    public TournamentController(GUI frame) {
        this.frame = frame;
    }
    
    public void fillTournamentData() {
        if (curTournamentId != -1){
            Tournament t = tournamentDao.findById(curTournamentId).get();
            
            frame.tournamentDialogNameData.setText(t.getName());
            frame.tournamentDialogSportTypeInfoData.setText(t.getSport());
            frame.tournamentDialogStartDateInfoData.setText(t.getStartTime().toString());
            frame.tournamentDialogEndInfoData.setText(t.getEndTime().toString());
        }
    }
    
    public DefaultTableModel getFullTableModel(){
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
                tournaments.get(i).getSport(), 
                tournaments.get(i).getName(),
                tournaments.get(i).getStartTime()
            });
        }
        
        return model;
    }
}

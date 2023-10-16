package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.infrastructure.data.DaoFactory;
import br.ucs.classleague.infrastructure.data.TournamentDao;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.util.ArrayList;
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
    
    public DefaultTableModel updateTournamentListCells() {
        List<Tournament> tournaments = new ArrayList<>();
        
        try {
            tournaments = tournamentDao.findAll();
        } catch (Exception e){  
            e.printStackTrace();
            return getTournamentListTableModel(0);
        }
        
        DefaultTableModel model = getTournamentListTableModel(tournaments.size());
                            
        for (int i = 0; i < tournaments.size(); i++) {
            model.setValueAt(tournaments.get(i).getId(), i, 0);
            model.setValueAt(tournaments.get(i).getSport(), i, 1);
            model.setValueAt(tournaments.get(i).getName(), i, 2);
            model.setValueAt(tournaments.get(i).getStartTime(), i, 3);
        }
        
        return model;
    }
        
    private DefaultTableModel getTournamentListTableModel(int rowCount) {
        String[] columnHeaders = new String[] {"ID", "Esporte", "Nome", "Data de início"};

        DefaultTableModel tournamentListModel = new DefaultTableModel(columnHeaders, rowCount) {
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        return tournamentListModel;
    }
}

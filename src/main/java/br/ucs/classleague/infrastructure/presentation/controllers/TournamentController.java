package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.infrastructure.presentation.views.GUI;
import javax.swing.table.DefaultTableModel;

public class TournamentController {
    
    private GUI frame;
    
    public TournamentController(GUI frame) {
        this.frame = frame;
    }
        
    public DefaultTableModel getTournamentListTableModel(int rowCount) {
        String[] columnHeaders = new String[] {"ID", "Esporte", "Nome", "Data de início"};

        DefaultTableModel tournamentListModel = new DefaultTableModel(columnHeaders, rowCount);
        
        return tournamentListModel;
    }
}

package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.application.Services.MatchService;
import br.ucs.classleague.domain.Match;
import br.ucs.classleague.infrastructure.presentation.model.MatchModel;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MatchPointsController {
    
    private GUI view;
    private MatchModel matchModel;
    private MatchService matchService;

    public MatchPointsController(GUI view, MatchModel matchModel) {
        this.view = view;
        this.matchModel = matchModel;
        matchService = new MatchService();
    }
    
    public void fillPointsComboBox() {
        Match match = matchModel.getMatch();
        view.addPointsComboBox.addItem(match.getSecond_team().getAcronym());
        view.addPointsComboBox.addItem(match.getFirst_team().getAcronym());
    }
    
    public void addPoints() {
        int row = view.pointsScoredTable.getSelectedRow();
        if (row == -1){
            return;
        } 
        
        int pointsAmount = (Integer) view.selectPointsSpinner.getValue();
        String studentId = view.pointsScoredTable.getValueAt(row, 0).toString();
        String teamAcronym = view.addPointsComboBox.getSelectedItem().toString();
        
        if (view.addPointsComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Selecione um time e/ou jogador para destinar os pontos.");
            return;
        }

        int newPointAmount = matchService.updatePointsForPlayer(
                pointsAmount, 
                Long.parseLong(studentId), 
                matchService.getTeamWithAcronym(teamAcronym).getId()
        );
        
        view.pointsScoredTable.clearSelection();
        view.pointsScoredTable.setValueAt(newPointAmount, row, 2);
    }
    
    public DefaultTableModel fillTeamList() {                   
        String teamAcronym = view.addPointsComboBox.getSelectedItem().toString();

        DefaultTableModel model = (DefaultTableModel) view.pointsScoredTable.getModel();
        List<Object[]> teamStudentsToObjectArray = matchService.teamStudentsToObjectArray(teamAcronym);
        
        for (Object[] row : teamStudentsToObjectArray) {
            model.addRow(row);     
        }
        
        return model;
    }
    
    public void resetPointsComponents() {
        view.addPointsComboBox.removeAllItems();
        view.selectPointsSpinner.setValue(0);
        ControllerUtilities.resetTable(view.pointsScoredTable);
    }
    
}

package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.application.Services.MatchService;
import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.Team;
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
        view.addPointsComboBox.addItem(match.getFirst_team().getAcronym());
        view.addPointsComboBox.addItem(match.getSecond_team().getAcronym());
    }

    public void insertPoint(int point) {
        int row = view.pointsScoredTable.getSelectedRow();
        Object cbItem = view.addPointsComboBox.getSelectedItem();
        
        if (row == -1){
            return;
        }  
        
        if (cbItem == null) {
            JOptionPane.showMessageDialog(view.tournamentDialog, "Selecione um time e/ou jogador para destinar os pontos");
            return;
        }
        
        String studentId = view.pointsScoredTable.getValueAt(row, 0).toString();
        String teamAcronym = cbItem.toString();
        Team team = matchService.getTeamWithAcronym(teamAcronym);

        int playerScore = matchService.updatePointsForPlayer(
                point, 
                Long.parseLong(studentId), 
                team.getId()
        );
        
        view.pointsScoredTable.clearSelection();
        view.pointsScoredTable.setValueAt(playerScore, row, 2);
        
        int teamScore = matchService.updatePointsForTeam(
                matchModel.getMatch(),
                team.getId(),
                point
        );
        
        setTeamScore(teamScore);
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
        ControllerUtilities.resetTable(view.pointsScoredTable);
    }
    
    private void setTeamScore(Integer score) {
        int i = view.addPointsComboBox.getSelectedIndex();
        String scoreText = Integer.toString(score);
        
        if (i == 0) {
            view.firstTeamScoreLabel.setText(scoreText);
        } else {
            view.secondTeamScoreLabel.setText(scoreText);
        }
    }
    
}

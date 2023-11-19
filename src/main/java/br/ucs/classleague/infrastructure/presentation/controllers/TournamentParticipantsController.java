package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.domain.Team;
import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.domain.TournamentTeam;
import br.ucs.classleague.infrastructure.data.DaoProvider;
import br.ucs.classleague.infrastructure.data.TeamDao;
import br.ucs.classleague.infrastructure.data.TournamentTeamDao;
import br.ucs.classleague.infrastructure.presentation.model.TournamentModel;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TournamentParticipantsController {
    
    private GUI view;
    private TournamentModel tournamentModel;
    private TeamDao teamDao;
    private TournamentTeamDao ttDao;
    
    public TournamentParticipantsController(GUI view, TournamentModel tournamentModel) {
        this.view = view;
        this.tournamentModel = tournamentModel;
        this.ttDao = DaoProvider.getTournamentTeamDao();
    }
    
    public void fillTournamentParticipantsList() {
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
}
